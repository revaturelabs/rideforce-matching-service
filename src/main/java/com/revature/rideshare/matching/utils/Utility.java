package com.revature.rideshare.matching.utils;

import com.revature.rideshare.matching.beans.User;

public class Utility {

	public static double distance(User u1, User u2) {
		return distance(u1.getLocation().getLatitude(), u1.getLocation().getLongitude(), u2.getLocation().getLatitude(),
				u2.getLocation().getLongitude(), "M");
	}

	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		return dist;
	}

}
