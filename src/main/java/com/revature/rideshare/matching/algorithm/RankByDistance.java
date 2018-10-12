package com.revature.rideshare.matching.algorithm;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.MapsClient;

public class RankByDistance extends RankingCriterion {
	
	@Autowired
	MapsClient mapsClient;

	@Override
	public double rank(User rider, User driver) {
		Route riderToDriver = mapsClient.getRoute(rider.getAddress(), driver.getAddress());
		return 1 / ((double) riderToDriver.getDistance() + 1);
	}

}
