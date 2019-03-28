package com.revature.rideshare.matching.filters;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;

public class StatusFilter implements ListFilter<User> {

	private String status;

//	public StatusFilter() {
//		super();
//		this.status = "active";
//	}

	public StatusFilter(String status) {
		super();
		this.status = status;
	}

	@Override
	public boolean filter(User e) {
		return e.isActive().equalsIgnoreCase(status);
	}

}
