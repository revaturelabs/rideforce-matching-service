package com.revature.rideshare.matching.services;

import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideshare.matching.algorithm.AggregateRankingBuilder;
import com.revature.rideshare.matching.algorithm.RankByAffect;
import com.revature.rideshare.matching.algorithm.RankByBatchEnd;
import com.revature.rideshare.matching.algorithm.RankByDistance;
import com.revature.rideshare.matching.algorithm.RankByStartTime;
import com.revature.rideshare.matching.beans.Filter;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.UserClient;

/**
 * The main service class for finding drivers who match a given rider.
 */
@Service
public class MatchService {

	/** The Constant LOGGER. */

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchService.class);

	/**
	 * The maximum number of matches to find. Configured in matching.properties.
	 */
	private static int maxMatches;

	/**
	 * The coefficients used to weight importance of matching features. These are
	 * configured in matching.properties.
	 */

	/**
	 * Can change to impact weight of distance between rider and driver in algorithm
	 */
	private static double distanceCoefficient;

	/** Can change to impact weight of batch end of rider compared to driver */
	private static double batchEndCoefficient;

	/** Can change to impact weight of rider opinion of driver affect */
	private static double affectCoefficient;

	/**
	 * Can change to impact weight of daily start time of rider compared to driver
	 */
	private static double startTimeCoefficient;

	
	/** 
	 * The string representation of the match service property, max matches. 
	 * This references the matching.properties file. 
	 */
	public static final String MAX_MATCHES_STR = "max_matches";
	
	/** 
	 * The string representation of the match service property, distance coefficient. 
	 * This references the matching.properties file. 
	 */
	public static final String DISTANCE_COEFFICIENT_STR = "distance_coefficient";
	
	/** 
	 * The string representation of the match service property, batch end coefficient. 
	 * This references the matching.properties file. 
	 */
	public static final String BATCH_END_COEFFICIENT_STR = "batch_end_coefficient";
	
	/** 
	 * The string representation of the match service property, affect coefficient. 
	 * This references the matching.properties file. 
	 */
	public static final String AFFECT_COEFFICIENT_STR = "affect_coefficient";
	
	/** 
	 * The string representation of the match service property, start time coefficient. 
	 * This references the matching.properties file. 
	 */
	public static final String START_TIME_COEFFICIENT_STR = "start_time_coefficient";
	
	
	/**
	 * The properties were configured in matching.properties file. See setup method.
	 */
	Map<String, Double> property = MatchService.setup();
	{
		maxMatches = property.get(MAX_MATCHES_STR).intValue();
		distanceCoefficient = property.get(DISTANCE_COEFFICIENT_STR);
		batchEndCoefficient = property.get(BATCH_END_COEFFICIENT_STR);
		affectCoefficient = property.get(AFFECT_COEFFICIENT_STR);
		startTimeCoefficient = property.get(START_TIME_COEFFICIENT_STR);		
	}

	/**
	 * The role corresponding to a potential driver.
	 */
	private static final String DRIVER_ROLE = "DRIVER";

	/** Feign client to User Service */
	@Autowired
	private UserClient userClient;

	private static RankByAffect rankByAffect;
	private static RankByBatchEnd rankByBatchEnd;
	private static RankByDistance rankByDistance;
	private static RankByStartTime rankByStartTime;
	{
		rankByAffect = new RankByAffect();
		rankByBatchEnd = new RankByBatchEnd();
		rankByDistance = new RankByDistance();
		rankByStartTime = new RankByStartTime();

		rankByAffect.setWeight(affectCoefficient);
		rankByBatchEnd.setWeight(batchEndCoefficient);
		rankByDistance.setWeight(distanceCoefficient);
		rankByStartTime.setWeight(startTimeCoefficient);
	}

	/**
	 * An association of a user with a rank.
	 */
	private static class RankedUser implements Comparable<RankedUser> {

		/**
		 * The user in the association.
		 */
		private User user;
		/**
		 * The matching rank, as defined by
		 * {@link com.revature.rideshare.matching.services.MatchService#rankMatch(User, User)
		 * rankMatch}.
		 */
		private double rank;

		/**
		 * Instantiates a new ranked user.
		 *
		 * @param user the user
		 * @param rank the rank
		 */
		public RankedUser(User user, double rank) {
			this.user = user;
			this.rank = rank;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(RankedUser o) {
			return Double.compare(rank, o.rank);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(rank);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			RankedUser other = (RankedUser) obj;
			if (Double.doubleToLongBits(rank) != Double.doubleToLongBits(other.rank)) {
				return false;
			}
			if (user == null) {
				if (other.user != null) {
					return false;
				}
			} else if (!user.equals(other.user)) {
				return false;
			}
			return true;
		}

	}

	/**
	 * Finds matched drivers for given riders by distance. Association formed
	 * between driver and a ranking, with ranking discarded after sorting.
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, sorted by distance in descending
	 *         order (up to {@link #maxMatches})
	 */
	public List<User> findMatchesByDistance(User rider) {
		if (rider != null) {
			LOGGER.debug("findMatchesByDistance recieved user: {}", rider.getFirstName());
		} else {
			LOGGER.error("RECIEVED A NULL USER: findMatchesByDistance in matchService.");
			throw new NullPointerException();
		}
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		arb.addCriterion(rankByDistance);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matched drivers for given rider by office location only, based on if
	 * they are liked or disliked drivers (affected drivers).
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, minus affected (up to
	 *         {@link #maxMatches})
	 */
	public List<User> findMatchesByAffects(User rider) {
		if (rider != null) {
			LOGGER.debug("findMatchesByAffects recieved user: {}", rider.getFirstName());
		} else {
			LOGGER.error("RECIEVED A NULL USER: findMatchesByAffects in matchService.");
			throw new NullPointerException();
		}
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		arb.addCriterion(rankByAffect);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matched drivers for rider based on the batch's end date. Association
	 * formed between driver and a ranking, with rank discarded after sorting.
	 * 
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, sorted by batch end date in
	 *         descending order
	 */
	public List<User> findMatchesByBatchEnd(User rider) {
		if (rider != null) {
			LOGGER.debug("findMatchesByBatchEnd recieved user: {}", rider.getFirstName());
		} else {
			LOGGER.error("RECIEVED A NULL USER: findMatchesByBatchEnd in matchService.");
			throw new NullPointerException();
		}
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		arb.addCriterion(rankByBatchEnd);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matched drivers for rider based on start time. Association formed
	 * between driver and a ranking, with rank discarded after sorting.
	 *
	 * @param rider the user looking for a ride
	 * @return unranked list of matched drivers, sorted by daily start time in
	 *         descending order
	 */
	public List<User> findMatchesByStartTime(User rider) {
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		arb.addCriterion(rankByStartTime);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matched drivers for rider based on a weighted rank from distance, batch
	 * end, daily start time, and whether they are liked or disliked drivers
	 * (affected drivers).
	 * 
	 * @param rider the user for whom to find a driver
	 * @return list of matched drivers, sorted by nearest distance and closest batch
	 *         end date in descending order (up to {@link #maxMatches})
	 */
	public List<User> findMatches(User rider) {
		if (rider != null) {
			LOGGER.debug("findMatches recieved user: {}", rider.getFirstName());
		} else {
			LOGGER.error("RECIEVED A NULL USER: findMatches in matchService.");
			throw new NullPointerException();
		}
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		arb.addCriterion(rankByAffect);
		arb.addCriterion(rankByBatchEnd);
		arb.addCriterion(rankByDistance);
		arb.addCriterion(rankByStartTime);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
	}

	/**
	 * Finds matches based on the criteria specified in the filter object.
	 * 
	 * @param filter the filter we use to determine which criteria to use
	 * @param rider  the rider for whom we determine suitable matches
	 * @return a list of drivers who fit our matching criteria
	 */
	public List<User> findFilteredMatches(Filter filter, User rider) {
		int officeId = officeLinkToId(rider.getOffice());
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		if (filter.isBatchEndChange()) {
			arb.addCriterion(rankByBatchEnd);
		}
		if (filter.isDayStartChange()) {
			arb.addCriterion(rankByStartTime);
		}
		if (filter.isDistanceChange()) {
			arb.addCriterion(rankByDistance);
		}
		arb.addCriterion(rankByAffect);

		return userClient.findByOfficeAndRole(officeId, DRIVER_ROLE).stream()
				.map(driver -> new RankedUser(driver, arb.rankMatch(rider, driver))).sorted(Comparator.reverseOrder())
				.limit(maxMatches).map(rankedUser -> rankedUser.user).collect(Collectors.toList());
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
	 * A function used to populate our property map with externally configured
	 * values
	 * 
	 * @return a map of properties to use in this class
	 */
	private static Map<String, Double> setup() {
		Properties prop = new Properties();
		String path = "src/main/resources/matching.properties";
		try {
			prop.load(new FileReader(path));
		} catch (IOException e) {
			LOGGER.error("Properties file not found! " + e);
		}

		Map<String, Double> values = new HashMap<>();

		values.put(MAX_MATCHES_STR, Double.parseDouble(prop.getProperty(MAX_MATCHES_STR)));
		values.put(DISTANCE_COEFFICIENT_STR, Double.parseDouble(prop.getProperty(DISTANCE_COEFFICIENT_STR)));
		values.put(BATCH_END_COEFFICIENT_STR, Double.parseDouble(prop.getProperty(BATCH_END_COEFFICIENT_STR)));
		values.put(AFFECT_COEFFICIENT_STR, Double.parseDouble(prop.getProperty(AFFECT_COEFFICIENT_STR)));
		values.put(START_TIME_COEFFICIENT_STR, Double.parseDouble(prop.getProperty(START_TIME_COEFFICIENT_STR)));

		return values;
	}
}