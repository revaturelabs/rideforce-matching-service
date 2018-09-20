package com.revature.rideshare.matching.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.matching.beans.Link;
import com.revature.rideshare.matching.beans.User;

@RestController
@RequestMapping("matches")
public class MatchingController {
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Link<User>> getAll(@PathVariable int id) {
		List<Link<User>> matches = null;
		return matches;
	}
	
	@RequestMapping(value="/likes/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Link<User>> getLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		List<Link<User>> likes = null;
		return likes;
	}
	
	@RequestMapping(value="/likes/{id}/{liked}", method=RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		
	}
	
	@RequestMapping(value="/likes/{id}/{liked}", method=RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		
	}	
	
	@RequestMapping(value="dislikes/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Link<User>> getDisliked(@PathVariable("id") int id) {
		List<Link<User>> dislikes = null;
		return dislikes;
	}
	
	@RequestMapping(value="/dislikes/{id}/{disliked}", method=RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		
	}
	
	@RequestMapping(value="/dislikes/{id}/{disliked}", method=RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		
	}

}
