package com.revature.rideshare.matching.comparators;

import java.util.Comparator;

import com.revature.rideshare.matching.beans.CachedLocation;
import com.revature.rideshare.matching.beans.User;

public class ProximityComparator implements Comparator<User> {

	private CachedLocation riderLocation;

	public ProximityComparator(User rider) {
		this.riderLocation = rider.getLocation();
	}

	@Override
	public int compare(User driver1, User driver2) {
		CachedLocation d1l = driver1.getLocation();
		CachedLocation d2l = driver2.getLocation();
		double d1 = Math.sqrt(Math.pow(d1l.getLatitude() - riderLocation.getLatitude(), 2)
				+ Math.pow(d1l.getLongitude() - riderLocation.getLongitude(), 2));
		double d2 = Math.sqrt(Math.pow(d2l.getLatitude() - riderLocation.getLatitude(), 2)
				+ Math.pow(d2l.getLongitude() - riderLocation.getLongitude(), 2));
		return (d1 > d2) ? 1 : ((d1 < d2) ? -1 : 0);
	}

}