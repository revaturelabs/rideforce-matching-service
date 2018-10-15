package com.revature.rideshare.matching.clients;

import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.rideshare.matching.beans.User;

/** This class is the fallback implementation for when a service cannot 
 * make a successful connection with a UserClient. The general behavior
 * is to give a ResponseError stating that a connection could not be 
 * made. */
@Component
public class UserClientFallback implements UserClient {

	@Override
	public List<User> findByOfficeAndRole(int officeId, String role) {
		return null;
	}

	@Override
	public User findById(int id) {
		return null;
	}


}
