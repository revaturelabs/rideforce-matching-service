package com.revature.rideshare.matching.filters;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;
import com.revature.rideshare.matching.utils.Utility;

public class ProximityFilter implements ListFilter<User> {

	private User rider;
	private double radius;

	/**
	 * This is a ListFilter intended to filter a list of users within a maximum
	 * distance radius around a rider
	 * 
	 * @param rider  the rider
	 * @param radius the maximum radius in miles
	 */
	public ProximityFilter(User rider, double radius) {
		super();
		this.rider = rider;
		this.radius = radius;
	}

	@Override
	public boolean filter(User e) {
		return Utility.distance(rider, e) < radius;
	}

}
