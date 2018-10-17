package com.revature.rideshare.matching.algorithm;

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
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}
	
	
	/**
	 * Returns a hash code for this object. The hashcode functionality is 
	 * specified in the parent class, as the exact algorithm for differentiating 
	 * different subclasses is defined there. 
	 * This method is still provided here to avoid code smells. 
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * Used to constrain that all instances of a child class are viewed as equal.
	 * This allows for only unique criterion to be stored as set items and map keys. 
	 * The super class method is used to define the functionality. 
	 * This method is still provided here to avoid code smells. 
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
