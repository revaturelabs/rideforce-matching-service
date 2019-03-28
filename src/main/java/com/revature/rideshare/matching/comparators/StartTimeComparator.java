package com.revature.rideshare.matching.comparators;

import java.util.Comparator;

import com.revature.rideshare.matching.beans.User;

/**
 * TODO: Javadoc
 * 
 * @author Sanford
 *
 */
public class StartTimeComparator implements Comparator<User> {

	private float riderStartTime;

	public StartTimeComparator(User rider) {
		this.riderStartTime = rider.getStartTime();
	}

	@Override
	public int compare(User driver1, User driver2) {
		float st1 = Math.abs(driver1.getStartTime() - riderStartTime);
		float st2 = Math.abs(driver2.getStartTime() - riderStartTime);

		return (st1 > st2) ? 1 : ((st1 < st2) ? -1 : 0);
	}

}
