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
import com.revature.rideshare.matching.services.DislikeService;
import com.revature.rideshare.matching.services.LikeService;
import com.revature.rideshare.matching.services.MatchService;

@RestController
@RequestMapping("matches")
public class MatchingController {
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
		return matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getLiked(@PathVariable("id") int id) {
		List<String> likes = null;
		//TODO: Uncomment to test with real users!
//		likes = likeService.getLikes(id).stream()
//				.map(like -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(like.getPair().getAffectedId()).toString())
//				.collect(Collectors.toList());
		likes = likeService.getLikes(id).stream()
				.map(like -> like.toString())
				.collect(Collectors.toList());
		return likes;
	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		likeService.saveLike(id,liked);
	}

	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		likeService.deleteLike(id,liked);
	}

	@RequestMapping(value = "dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDisliked(@PathVariable("id") int id) {
		List<String> dislikes = null;
		//TODO: Uncomment to test with real users!
//		dislikes = dislikeService.getDislikes(id).stream()
//				.map(dislike -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(dislike.getPair().getAffectedId()).toString())
//				.collect(Collectors.toList());
		dislikes = dislikeService.getDislikes(id).stream()
				.map(dislike -> dislike.toString())
				.collect(Collectors.toList());
		return dislikes;
	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		dislikeService.saveDislike(id, disliked);
	}

	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		dislikeService.deleteDislike(id, disliked);
	}
}
