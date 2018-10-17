package com.revature.rideshare.matching.algorithm;

import com.revature.rideshare.matching.beans.User;

/**
 * Class used to rank a rider driver pair by batch end date
 * 
 * @author Julie
 *
 */

public class RankByStartTime extends RankingCriterion {

	/**
	 * Ranks a driver based on whether their start time is the same, earlier, or
	 * later than rider's. It is ranked with weights in descending order from same,
	 * earlier, or later.
	 *
	 * @param rider  the user looking for a ride
	 * @param driver the potential driver being ranked
	 * @return a double as a ranking value; higher is better
	 */

	@Override
	protected double rank(User rider, User driver) {
		if (rider == null || driver == null) {
			throw new IllegalArgumentException("Rider and driver should not be null");
		}

		if (rider.getStartTime() > driver.getStartTime()) {
			return 0.5;
		} else if (rider.getStartTime() == driver.getStartTime()) {
			return 1;
		}
		return 0;
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
