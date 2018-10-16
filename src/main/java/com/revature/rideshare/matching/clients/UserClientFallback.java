package com.revature.rideshare.matching.clients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.FeignClientFallbackException;

/** This class is the fallback implementation for when a service cannot 
 * make a successful connection with a UserClient. The general behavior
 * is to give a ResponseError stating that a connection could not be 
 * made. */
@Component
public class UserClientFallback implements UserClient {

	@Override
	public List<User> findByOfficeAndRole(int officeId, String role) {
		// Note, a different, but similar exception is actually thrown if the 
		// fallback throws an exception. 
		throw new FeignClientFallbackException("Counldn't connect to the UserClient");
	}

	@Override
	public User findById(int id) {
		// Note, a different, but similar exception is actually thrown if the 
		// fallback throws an exception. 
		throw new FeignClientFallbackException("Counldn't connect to the UserClient");
	}


}
