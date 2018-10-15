package com.revature.rideshare.matching.algorithm;

import com.revature.rideshare.matching.beans.User;

/**
 * Class used to rank a rider driver pair by batch end date
 * 
 * @author Ray
 *
 */
public class RankByBatchEnd extends RankingCriterion {

	/**
	 * Ranks a driver based on if their batch ends sooner or later than the rider's
	 * batch. If rider ends later than driver, driver is ranked lower. Time is
	 * calculated in milliseconds and converted to days.
	 * 
	 * @param rider  the user looking for a ride
	 * @param driver the potential driver being ranked
	 * @return a double as ranking value; higher is better
	 */
	@Override
	protected double rank(User rider, User driver) {
        long diffInMilli;
        long diff;

        if(rider.getBatchEnd().compareTo(driver.getBatchEnd()) > 0) {
            diffInMilli = Math.abs(driver.getBatchEnd().getTime() - rider.getBatchEnd().getTime());
            diff = diffInMilli/(86400000); // Convert the difference to days instead of milliseconds 
            return 1/((double) diff + 1);
        } else {
            return 1;
        }
	}

}
