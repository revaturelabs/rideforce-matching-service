package com.revature.rideshare.matching.clients;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.rideshare.matching.beans.User;

/** This class is the fallback implementation for when a service cannot 
 * make a successful connection with a UserClient. The general behavior
 * is to give a ResponseError stating that a connection could not be 
 * made. */
@Component
public class UserClientFallback implements UserClient {
	static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public List<User> findByOfficeAndRole(int officeId, String role) {
		// Note, a different, but similar exception is actually thrown if the 
		// fallback throws an exception.
		logger.info("findByOfficeAndRole() FALLBACK EXECUTED");
		return Collections.EMPTY_LIST;
	}

	@Override
	public User findById(int id) {
		// Note, a different, but similar exception is actually thrown if the 
		// fallback throws an exception.
		logger.info("findById() FALLBACK EXECUTED");
		return null;
	}

}
