package com.revature.rideshare.matching.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.MapsClient;

/**
 * Class used to rank a rider driver pair by distance
 * 
 * @author Ray
 *
 */
@Component
public class RankByDistance extends RankingCriterion {

	/**
	 * The feign client used to connect to our maps service
	 */
	@Autowired
	private MapsClient mapsClient;

	/**
	 * Ranks how well the given driver matches the given rider.
	 * 
	 * @param rider  the rider under consideration
	 * @param driver the potential driver, whose suitability is to be determined
	 * @return a ranking value, where higher is better
	 */
	@Override
	protected double rank(User rider, User driver) {
//		System.out.println("Executing rank in RankByDistance");
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
//		System.out.println("riderToDriver in RankByDistance class: " + riderToDriver.toString());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}

}
