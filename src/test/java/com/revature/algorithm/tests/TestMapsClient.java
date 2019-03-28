package com.revature.algorithm.tests;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.clients.MapsClient;

public class TestMapsClient implements MapsClient {

	private Route route;

	@Override
	public Route getRoute(String start, String end) {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}
