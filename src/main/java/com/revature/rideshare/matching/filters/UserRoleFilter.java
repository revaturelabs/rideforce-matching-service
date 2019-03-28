package com.revature.rideshare.matching.filters;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;

/**
 * TODO: Javadoc
 * 
 * @author Sanford
 *
 */
public class UserRoleFilter implements ListFilter<User> {

	// Maybe turn this into enum
	private String role;
	
	public UserRoleFilter(String role) {
		super();
		this.role = role;
	}
	
	@Override
	public boolean filter(User e) {
		return e.getRole().equals(role);
	}
	
}
