package com.revature.rideshare.matching.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/** This class is the fallback implementation for when a service cannot 
 * make a successful connection with a MapClient. The general behavior
 * is to give a ResponseError stating that a connection could not be 
 * made. */
@Component
public class MapsClientFallback implements MapsClient {

	@Override
	public ResponseEntity<?> getRoute(String start, String end) {
//		throw new ConnectException("Couldn't Connect to map Client");
		//TODO: Temporary for testing. 
		return null;
	}


}
