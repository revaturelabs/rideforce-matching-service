package com.revature.rideshare.matching.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingTestController {
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test() {
		return "matching controller works!";
	}

}
