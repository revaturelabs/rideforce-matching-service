package com.revature.rideshare.matching.algorithm;

import org.springframework.stereotype.Component;

import com.revature.rideshare.matching.beans.User;

/**
 * Class used to rank a rider-driver pair by batch end date
 * 
 * @author Ray
 *
 */
@Component
public class RankByBatchEnd extends RankingCriterion {

	/**
	 * Ranks a driver based on if their batch ends sooner or later than the rider's
	 * batch. If rider ends later than driver, driver is ranked lower. Time is
	 * calculated in milliseconds and converted to days.
	 * 
	 * @param rider  the user looking for a ride
	 * @param driver the potential driver being ranked
	 * @return a double between 0 and 1 representing a rider-driver ranking value;
	 *         higher is better
	 */
	@Override
	protected double rank(User rider, User driver) {
		if (rider == null || driver == null) {
			throw new IllegalArgumentException("Rider and driver should not be null");
		}
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
	 * Returns a hash code for this object. The hashcode functionality is specified
	 * in the parent class, as the exact algorithm for differentiating different
	 * subclasses is defined there. This method is still provided here to avoid code
	 * smells.
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Used to constrain that all instances of a child class are viewed as equal.
	 * This allows for only unique criterion to be stored as set items and map keys.
	 * The super class method is used to define the functionality. This method is
	 * still provided here to avoid code smells.
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
