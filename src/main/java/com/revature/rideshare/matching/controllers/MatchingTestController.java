package com.revature.rideshare.matching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.matching.clients.UserClient;

// TODO: Auto-generated Javadoc
/**
 * The Class MatchingTestController.
 */
@RestController
public class MatchingTestController {
	
	/** The user client. */
	@Autowired
	private UserClient userClient;

	/**
	 * Tests Matching controller connection.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return userClient.findByOfficeAndRole(1, "DRIVER").toString();
	}
}
