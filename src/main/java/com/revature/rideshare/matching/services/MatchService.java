package com.revature.rideshare.matching.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.MapsClient;
import com.revature.rideshare.matching.clients.UserClient;

/**
 * The main service class for finding drivers who match a given rider.
 */
@Service
public class MatchService {
	/**
	 * The maximum number of matches to find.
	 */
	public static final int MAX_MATCHES = 10;

	/**
	 * The role corresponding to a potential driver.
	 */
	private static final String DRIVER_ROLE = "DRIVER";

	@Autowired
	private MapsClient mapsClient;

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private DislikeService dislikeService;

	/**
	 * An association of a user with a rank.
	 */
	private class RankedUser implements Comparable<RankedUser> {
		/**
		 * The user in the association.
		 */
		public User user;
		/**
		 * The matching rank, as defined by
		 * {@link com.revature.rideshare.matching.services.MatchService#rankMatch(User, User)
		 * rankMatch}.
		 */
		public double rank;

		public RankedUser(User user, double rank) {
			this.user = user;
			this.rank = rank;
		}

		@Override
		public int compareTo(RankedUser o) {
			return Double.compare(rank, o.rank);
		}
	}

	/**
	 * Finds several drivers who would be good matches for the given rider.
	 * 
	 * @param rider the rider for whom to find drivers
	 * @return the drivers who match the given rider (up to {@link #MAX_MATCHES})
	 */
	public List<User> findMatchesByDistance(User rider) {
		int officeId = officeLinkToId(rider.getOffice());
		// Here, we find all potential drivers. We associate each with a
		// ranking, and then sort by ranking (descending). We take the first
		// MAX_MATCHES matches, discard the ranking, and collect the results in
		// a list.
		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(MAX_MATCHES).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}
	
	/**
	 * Find drivers who would be good drivers. Rank drivers who the rider has
	 * liked or disliked (affected drivers)
	 * 
	 * @param rider the rider for whom to find drivers
	 * @return the list if drivers who match the given rider, minus affected (up to {@link #MAX_MATCHES})
	 */
	public List<User> findMatchesByAffects(User rider) {
		int officeId = officeLinkToId(rider.getOffice());
		List<User> drivers = null;
		List<Integer> likes = getLikedIds(rider);
		List<Integer> dislikes = getDislikedIds(rider);
		drivers = userClient.findByOfficeAndRole(officeId, rider.getRole()).stream()
				.map(driver -> new RankedUser(driver, rankByAffect(rider, driver, likes, dislikes)))
				.sorted(Comparator.reverseOrder())
				.limit(MAX_MATCHES).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
		return drivers;
	}
	
	/**
	 * Finds drivers based on a weighted rank from distance and batch-end
	 * 
	 * @param rider for whom to find suitable drivers
	 * @return	the drivers who match the given rider (up to {@link #MAX_MATCHES})
	 */
	public List<User> findMatches(User rider) {
		int officeId = officeLinkToId(rider.getOffice());
		List<User> drivers = null;
		// Find all potential drivers, filter out those who are liked and 
		// disliked by the rider. Rank them based on distance and batch end. 
		// Then sort the list and return the first MAX_MATCHES matches
		
		return drivers;
	}

	/**
	 * Ranks how well the given driver matches the given rider.
	 * 
	 * @param rider  the rider under consideration
	 * @param driver the potential driver, whose suitability is to be determined
	 * @return a ranking value, where higher is better
	 */
	private double rankMatch(User rider, User driver) {
		// Right now, this only takes distance into consideration.
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}
	
	
	/**
	 * Ranks a rider based on whether they have been liked or disliked
	 * 
	 * @param rider the rider under consideration
	 * @param driver the potential driver who we are ranking
	 * @param likes a list of driver IDs for whom the rider has liked. Consult the getLikedIds() function below
	 * @param dislikes a list of driver IDs for whom the rider has disliked. Consult the getDislikedIds() function below
	 * @return
	 */
	private double rankByAffect(User rider, User driver, List<Integer> likes, List<Integer> dislikes) {
		// Determines whether the user has a good affect, and ranks them accordingly
		if(dislikes.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 0.0;
		} else if(likes.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 1.0;
		} else {
			return 0.5;
		}
	}

	/**
	 * Extracts the office ID from an office link.
	 * 
	 * @param link a link to an office (e.g. "/offices/2")
	 * @return the ID of the office
	 * @throws IllegalArgumentException if the given link is invalid
	 */
	private int officeLinkToId(String link) {
		AntPathMatcher matcher = new AntPathMatcher();
		try {
			return Integer.parseInt(matcher.extractUriTemplateVariables("/offices/{id}", link).get("id"));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid office link.");
		}
	}
	
	/**
	 * Returns a list of liked driver IDs. Used to reduce the number of calls for 
	 * this functionality in the ranking mechanism
	 * 
	 * @param rider the rider for whom we want to find the correspond likes
	 * @return
	 */
	private List<Integer> getLikedIds(User rider) {
		return likeService.getLikes(rider.getId()).stream()
				.map(like -> like.getPair().getAffectedId()).collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of disliked driver IDs. Used to reduce the number of calls for 
	 * this functionality in the ranking mechanism
	 * 
	 * @param rider the rider for whom we want to find the correspond likes
	 * @return
	 */
	private List<Integer> getDislikedIds(User rider) {
		return dislikeService.getDislikes(rider.getId()).stream()
				.map(dislike -> dislike.getPair().getAffectedId()).collect(Collectors.toList());
	}
}
