package com.revature.rideshare.matching.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.UserClient;
import com.revature.rideshare.matching.services.MatchService;

@RestController
@RequestMapping("matches")
public class MatchingController {
	@Autowired
	UserClient userClient;

	@Autowired
	MatchService matchService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAll(@PathVariable int id) {
		User rider = userClient.findById(id);
		return matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		List<String> likes = null;
		return likes;
	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {

	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {

	}

	@RequestMapping(value = "dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDisliked(@PathVariable("id") int id) {
		List<String> dislikes = null;
		return dislikes;
	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("liked") int liked) {

	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {

	}
}
