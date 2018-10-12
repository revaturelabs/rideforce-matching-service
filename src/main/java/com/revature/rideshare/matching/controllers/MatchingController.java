package com.revature.rideshare.matching.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.clients.UserClient;
import com.revature.rideshare.matching.services.DislikeService;
import com.revature.rideshare.matching.services.LikeService;
import com.revature.rideshare.matching.services.MatchService;

@RestController
@RequestMapping("matches")
public class MatchingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchingController.class);
	private static final String MSG = "Get request to matching controller made with UserId : %d passed. userClient called to find user by that id. userClient returned the user: %d";
	private static final String NULL = "userClient return a null user object.";
	@Autowired
	UserClient userClient;

	@Autowired
	MatchService matchService;

	@Autowired
	LikeService likeService;

	@Autowired
	DislikeService dislikeService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAll(@PathVariable int id) {
		User rider = userClient.findById(id);
		if (rider == null) {
			LOGGER.trace(NULL);
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
		}
		return matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/likes-dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllMinusAffects(@PathVariable int id) {
		
		User rider = userClient.findById(id);
		if (rider == null) {
			LOGGER.trace(NULL);
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
		}
		return matchService.findMatchesByAffects(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/distance/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByDistance(@PathVariable int id) {
		User rider = userClient.findById(id);
		if (rider == null) {
			LOGGER.trace(NULL);
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
		}
		return matchService.findMatchesByDistance(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());

	}

	@RequestMapping(value = "/batch-end/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByBatchEnd(@PathVariable int id) {
		User rider = userClient.findById(id);
		if (rider == null) {
			LOGGER.trace(NULL);
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
		}
		return matchService.findMatchesByBatchEnd(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getLiked(@PathVariable("id") int id) {
		List<String> likes = null;

		likes = likeService.getLikes(id).stream().map(like -> UriComponentsBuilder.fromPath("/users/{id}")
				.buildAndExpand(like.getPair().getAffectedId()).toString()).collect(Collectors.toList());

		if (likes.isEmpty()) {
			LOGGER.trace("Mapping process did not return any URIs associated with this user id: %d", id);
		} else {
			LOGGER.info("likeService.getLikes called with id: %d which is then mapped to create a list of uri's that contain a path to "
					+ "to get the users (drivers) that they have liked.", id);
		}
		
		return likes;
	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		LOGGER.info("Like service called to save a like for the userId %d and affected userId %d.", id, liked);
		likeService.saveLike(id, liked);
	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		LOGGER.info("Like service called to delete a like for the userId %d and affected userId %d.", id, liked);
		likeService.deleteLike(id, liked);
	}

	@RequestMapping(value = "dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDisliked(@PathVariable("id") int id) {
		List<String> dislikes = null;
		
		dislikes = dislikeService.getDislikes(id).stream().map(dislike -> UriComponentsBuilder.fromPath("/users/{id}")
				.buildAndExpand(dislike.getPair().getAffectedId()).toString()).collect(Collectors.toList());

		if (dislikes.isEmpty()) {
			LOGGER.trace("Mapping process did not return any URIs associated with this user id: %d ", id);
		} else {
			LOGGER.info("dislikeService.getDislikes called with id: %d which was then mapped to create a list of uri's that contain a path to "
					+ "to get the users (drivers) that they have liked.", id);
		}
		
		return dislikes;
	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		dislikeService.saveDislike(id, disliked);
		LOGGER.info("Dislike service called to save a dislike for the userId %d and affected userId %d.", id, disliked);
	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		
		dislikeService.deleteDislike(id, disliked);
		LOGGER.info("Dislike service called to delete a dislike for the userId %d and affected userId %d.", id, disliked);
	}
	
}

