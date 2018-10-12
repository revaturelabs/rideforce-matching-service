package com.revature.rideshare.matching.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign client for accessing the maps service.
 */
@FeignClient(name="maps-service", fallback=MapsClientFallback.class)
public interface MapsClient {
	/**
	 * Gets information for a route between two addresses.
	 * 
	 * @param start the start address
	 * @param end   the end address
	 * @return route information, consisting of the distance and duration of the
	 *         trip (on foot)
	 */
	@GetMapping(path = "/route", params = { "start", "end" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<?> getRoute(@RequestParam("start") String start, @RequestParam("end") String end);
}
