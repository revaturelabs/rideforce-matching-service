package com.revature.rideshare.matching.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchService.class);
	/**
	 * The maximum number of matches to find.
	 */
	private static final int MAX_MATCHES = 10;
	private static final double DISTANCE_COEFFICIENT = 1;
	private static final double BATCH_END_COEFFICIENT = 4;
	private static final double AFFECT_COEFFICENT = 1;
	// private static final double START_TIME_COEFFICIENT = 0;

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
	private static class RankedUser implements Comparable<RankedUser> {
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
			LOGGER.debug("comparing the rank: %d to user %s of rank: %d passed for comparison.", rank, o.user.getFirstName(), o.rank);
			return Double.compare(rank, o.rank);
		}
	}

	/**
	 * Finds matched drivers for given riders by distance. Association formed
	 * between driver and a ranking, with ranking discarded after sorting.
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, sorted by distance in descending
	 *         order (up to {@link #MAX_MATCHES})
	 */
	public List<User> findMatchesByDistance(User rider) {
		if(rider != null) {
			LOGGER.debug("Getting potential drivers for user: %s at office: %s. by distance", rider.getFirstName(), rider.getOffice());
		}else {
			LOGGER.error("Recieved null in findMatchesByDistance");
			//TODO add error handling for null : nothing should be processed without checking for null
		}
		int officeId = officeLinkToId(rider.getOffice());
		// Here, we find all potential drivers. We associate each with a
		// ranking, and then sort by ranking (descending). We take the first
		// MAX_MATCHES matches, discard the ranking, and collect the results in
		// a list.
		
		
		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, rankByDistance(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(MAX_MATCHES).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matched drivers for given rider by office location only, explicitly
	 * excluding liked and disliked drivers (affected drivers).
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, minus affected (up to
	 *         {@link #MAX_MATCHES})
	 */
	public List<User> findMatchesByAffects(User rider) {
		if(rider != null) {
			LOGGER.debug("Getting potential drivers for user: %s at office: %s. by affect", rider.getFirstName(), rider.getOffice());
		}else {
			LOGGER.error("Recieved null in findMatchesByAffects");
			//TODO add error handling for null : nothing should be processed without checking for null
		}
		int officeId = officeLinkToId(rider.getOffice());
		List<User> drivers = null;

		List<Integer> likes = getLikedIds(rider);
		List<Integer> dislikes = getDislikedIds(rider);

		drivers = userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, rankByAffect(rider, driver, likes, dislikes)))
				.sorted(Comparator.reverseOrder()).limit(MAX_MATCHES).map(rankedUser -> rankedUser.user)
				.collect(Collectors.toList());
		return drivers;
	}

	/**
	 * Finds matched drivers for rider based on the batch's end date. Association
	 * formed between driver and a ranking, with rank discarded after sorting.
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, sorted by batch end date in
	 *         descending order
	 */
	public List<User> findMatchesByBatchEnd(User rider){
		if(rider != null) {
			LOGGER.debug("Getting potential drivers for user: %s at office: %s. by batchend", rider.getFirstName(), rider.getOffice());
		}else {
			LOGGER.error("Recieved null in findMatchesByBatchEnd");
			//TODO add error handling for null : nothing should be processed without checking for null
		}
        int officeId = officeLinkToId(rider.getOffice());
        // Here, we find all potential drivers. We associate each with a
        // ranking, and then sort by ranking (descending). We take the first
        // MAX_MATCHES matches, discard the ranking, and collect the results in
        // a list.
        
        return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
                .map(driver -> new RankedUser(driver, rankByBatchEnd(rider, driver))).sorted(Comparator.reverseOrder())
                .limit(MAX_MATCHES).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
    }
	
	/**
	 * Finds matched drivers for rider based on a weighted rank from both distance
	 * and batch end, filtering out liked and disliked drivers (affected drivers).
	 * 
	 * @param rider the user for whom to find a driver
	 * @return list of matched drivers, sorted by nearest distance and closest batch
	 *         end date in descending order (up to {@link #MAX_MATCHES})
	 */
	public List<User> findMatches(User rider) {
		if(rider != null) {
			LOGGER.debug("Getting potential drivers for user: %s at office: %s.", rider.getFirstName(), rider.getOffice());
		}else {
			LOGGER.error("Recieved null in findMatches");
			//TODO add error handling for null : nothing should be processed without checking for null
		}
		int officeId = officeLinkToId(rider.getOffice());
		List<User> drivers = null;

		List<Integer> likes = getLikedIds(rider);
		List<Integer> dislikes = getDislikedIds(rider);

		drivers = userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, rankMatch(rider, driver, likes, dislikes)))
				.sorted(Comparator.reverseOrder()).limit(MAX_MATCHES).map(rankedUser -> rankedUser.user)
				.collect(Collectors.toList());
		return drivers;
	}

	// TODO: Add in batch start time as a weighted value; add in method for
	// getting batch start time

	/**
	 * Ranks drivers based on a weighted values from other ranking functions.
	 * 
	 * @param rider    the user looking for a ride
	 * @param driver   the user who can provide a ride and who is being ranked
	 * @param likes    a list of IDs for drivers whom the rider has liked. Consult
	 *                 the getLikedIds() function below
	 * @param dislikes a list of IDs for drivers whom the rider has disliked.
	 *                 Consult the getDislikedIds() function below
	 * @return double showing ranking of the driver, based on all ranking functions
	 */
	private double rankMatch(User rider, User driver, List<Integer> likes, List<Integer> dislikes) {
		/** These are the weights given to each individual category. */
		final double distanceCoefficient = 1;
		final double batchEndCoefficient = 4;
		final double affectCoefficient = 1;
		
		// Compute the individual rank metrics. 
		double distanceRank = rankByDistance(rider, driver);
		double batchEndRank = rankByBatchEnd(rider, driver);
		double affectRank = rankByAffect(rider, driver, likes, dislikes);
		// double startTimeRank = rankByStartTime(rider, driver);

		// TODO: add START_TIME_COEFFICIENT, startTimeRank)
		return (DISTANCE_COEFFICIENT * distanceRank + BATCH_END_COEFFICIENT * batchEndRank
				+ AFFECT_COEFFICENT * affectRank) / (DISTANCE_COEFFICIENT + BATCH_END_COEFFICIENT + AFFECT_COEFFICENT);
	}

	/**
	 * Ranks how well the given driver matches the given rider based on distance.
	 * 
	 * @param rider  the user looking for a ride
	 * @param driver the potential driver being ranked
	 * @return a double as ranking value; higher is better
	 */
	private double rankByDistance(User rider, User driver) {

		// TODO: This could be null based on the MapClient service.
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}

	/**
	 * Ranks a driver by whether they have been liked or disliked by rider.
	 * 
	 * @param rider    the user looking for a ride
	 * @param driver   the potential driver being ranked
	 * @param likes    a list of IDs for drivers whom the rider has liked. Consult
	 *                 the getLikedIds() function below
	 * @param dislikes a list of IDs for drivers whom the rider has disliked.
	 *                 Consult the getDislikedIds() function below
	 * @return a double as a rank
	 */
	private double rankByAffect(User rider, User driver, List<Integer> likes, List<Integer> dislikes) {
		// Determines whether the user has a good affect, and ranks them accordingly
		if (dislikes.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 0.0;
		} else if (likes.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 1.0;
		} else {
			return 0.5;
		}
	}

	/**
	 * Ranks a driver based on if their batch ends sooner or later than the rider's
	 * batch. If rider ends later than driver, driver is ranked lower. Time is
	 * calculated in milliseconds and converted to days.
	 * 
	 * @param rider  the user looking for a ride
	 * @param driver the potential driver being ranked
	 * @return a double as ranking value; higher is better
	 */
	private double rankByBatchEnd(User rider, User driver) {
		long diffInMilli;
		long diff;

		if (rider.getBatchEnd().compareTo(driver.getBatchEnd()) > 0) {
			diffInMilli = Math.abs(driver.getBatchEnd().getTime() - rider.getBatchEnd().getTime());
			diff = diffInMilli / (86400000); // Convert the difference to days instead of milliseconds
			return 1 / ((double) diff + 1);
		} else {
			return 1;
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
	 * Gets list of liked driver IDs. Used to reduce the number of calls for
	 * this functionality in the ranking mechanism.
	 * 
	 * @param rider the user who performed like action 
	 * @return unranked list of liked driver IDs corresponding to rider
	 */
	private List<Integer> getLikedIds(User rider) {
		return likeService.getLikes(rider.getId()).stream().map(like -> like.getPair().getAffectedId())
				.collect(Collectors.toList());
	}

	/**
	 * Gets list of disliked driver IDs. Used to reduce the number of calls for
	 * this functionality in the ranking mechanism.
	 * 
	 * @param rider the user who performed dislike action 
	 * @return unranked list of liked driver IDs corresponding to rider
	 */
	private List<Integer> getDislikedIds(User rider) {
		return dislikeService.getDislikes(rider.getId()).stream().map(dislike -> dislike.getPair().getAffectedId())
				.collect(Collectors.toList());
	}
}

