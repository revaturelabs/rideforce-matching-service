package com.revature.rideshare.matching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.matching.clients.MapsClient;

@RestController
public class MatchingTestController {
	@Autowired
	MapsClient mapsClient;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return mapsClient.getRoute("2925 Rensselaer Ct. Vienna, VA 22181", "11730 Plaza America Dr. Reston, VA")
				.toString();
	}
}
