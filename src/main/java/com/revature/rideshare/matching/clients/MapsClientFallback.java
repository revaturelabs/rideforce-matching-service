package com.revature.rideshare.matching.clients;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.rideshare.matching.beans.Route;

/**
 * This class is the fallback implementation for when a service cannot make a
 * successful connection with a MapClient. The general behavior is to give a
 * ResponseError stating that a connection could not be made.
 */
@Component
public class MapsClientFallback implements MapsClient {

	static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * Returns null. This fallback is executed when the we can't successfully
	 * receive input from the maps-service.
	 */
	@Override
	public Route getRoute(@RequestParam("start") String start, @RequestParam("end") String end) {
		logger.info("getRoute() FALLBACK EXECUTED");
		return null;
	}

}
