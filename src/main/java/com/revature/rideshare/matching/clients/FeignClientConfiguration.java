package com.revature.rideshare.matching.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignClientConfiguration {
	
	/** 
	 * Returns an interceptor that adds the Authentication header into
	 * Http requests made with feign client 
	 * @return An authenticator intercepter 
	 */
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		// Get the username and password from environmental variables
		String username = System.getenv("USER_SERVICE_USERNAME");
		String password = System.getenv("USER_SERVICE_PASSWORD");
		
		return new BasicAuthRequestInterceptor(username, password);
	}
	
}
