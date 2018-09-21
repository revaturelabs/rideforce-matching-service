package com.revature.rideshare.matching.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.rideshare.matching.beans.User;

/**
 * A Feign client for accessing the user service.
 */
@FeignClient("user-service")
public interface UserClient {
	/**
	 * Finds all the users who work at the given office and have the given role.
	 * 
	 * @param officeId the ID of the office whose employees to find
	 * @param role     the role of the users to find
	 * @return all users matching the office and role criteria
	 */
	@GetMapping(path = "/users", params = { "office", "role" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	List<User> findByOfficeAndRole(@RequestParam("office") int officeId, @RequestParam("role") String role);
}
