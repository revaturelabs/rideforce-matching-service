package com.revature.rideshare.matching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.revature.rideshare.matching.controllers.MatchingController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) throws Exception {
		LOGGER.trace("Trace Testing logging");
		LOGGER.debug("Debug Testing Logging");
		LOGGER.error("Error Testing Logging");
		LOGGER.warn(" Warn Testing Logging");
		SpringApplication.run(Application.class, args);
	}
}

