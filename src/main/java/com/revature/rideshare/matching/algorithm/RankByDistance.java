package com.revature.rideshare.matching.algorithm;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.MapsClient;

/**
 * Class used to rank a rider driver pair by distance
 * 
 * @author Ray
 *
 */
public class RankByDistance extends RankingCriterion {

	/**
	 * The feign client used to connect to our maps service
	 */
	@Autowired
	MapsClient mapsClient;

	/**
	 * Ranks how well the given driver matches the given rider.
	 * 
	 * @param rider  the rider under consideration
	 * @param driver the potential driver, whose suitability is to be determined
	 * @return a ranking value, where higher is better
	 */
	@Override
	protected double rank(User rider, User driver) {
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}

}
