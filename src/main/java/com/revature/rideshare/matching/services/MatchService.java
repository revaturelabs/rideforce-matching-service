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
	public List<User> findMatches(User rider) {
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
}
