package com.revature.rideshare.matching.filters;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;

public class ActivityFilter implements ListFilter<User> {

	public ActivityFilter() {
		super();
	}
	
	@Override
	public boolean filter(User e) {
		return e.isActive().equalsIgnoreCase("active");
	}

}
