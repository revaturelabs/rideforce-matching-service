package com.revature.rideshare.matching.comparators;

import java.util.Comparator;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.utils.Utility;

public class ProximityComparator implements Comparator<User> {

	private User rider;

	public ProximityComparator(User rider) {
		this.rider = rider;
	}

	@Override
	public int compare(User driver1, User driver2) {
		double d1 = Utility.distance(driver1, rider);
		double d2 = Utility.distance(driver2, rider);
		return (d1 > d2) ? 1 : ((d1 < d2) ? -1 : 0);
	}

}