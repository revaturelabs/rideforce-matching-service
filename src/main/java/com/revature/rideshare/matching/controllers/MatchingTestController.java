package com.revature.rideshare.matching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.matching.clients.UserClient;

@RestController
public class MatchingTestController {
	@Autowired
	private UserClient userClient;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return userClient.findByOfficeAndRole(1, "DRIVER").toString();
	}
}
