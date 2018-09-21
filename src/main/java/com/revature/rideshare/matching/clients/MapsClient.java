package com.revature.rideshare.matching.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.rideshare.matching.beans.Route;

/**
 * A Feign client for accessing the maps service.
 */
@FeignClient("maps-service")
public interface MapsClient {
	@GetMapping(path = "/route", params = { "start",
			"end" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Route getRoute(@RequestParam("start") String start, @RequestParam("end") String end);
}
