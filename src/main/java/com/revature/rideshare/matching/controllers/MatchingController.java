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

/**
 * The Class MatchingController.
 */
@RestController
@RequestMapping("matches")
public class MatchingController {
	
	/** The user client. */
	@Autowired
	UserClient userClient;

	/** The match service. */
	@Autowired
	MatchService matchService;
	
	/** The like service. */
	@Autowired
	LikeService likeService;
	
	/** The dislike service. */
	@Autowired
	DislikeService dislikeService;

	/**
	 * Gets all matched drivers to riders using rider's id as input and driver's id to get.
	 *
	 * @param id the rider's id
	 * @return list of matched drivers by their ids
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAll(@PathVariable int id) {
		User rider = userClient.findById(id);
		return matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}
	
	/**
	 *
	 * @param id the id
	 * @return the all minus affects
	 */
	@RequestMapping(value = "/likes-dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllMinusAffects(@PathVariable int id) {
		User rider = userClient.findById(id);
		return matchService.findMatchesByAffects(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the by distance.
	 *
	 * @param id the id
	 * @return the by distance
	 */
	@RequestMapping(value = "/distance/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByDistance(@PathVariable int id) {
		User rider = userClient.findById(id);
		return matchService.findMatchesByDistance(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());

	}
	
	/**
	 * Gets the by batch end.
	 *
	 * @param id the id
	 * @return the by batch end
	 */
	@RequestMapping(value = "/batch-end/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getByBatchEnd(@PathVariable int id){
        User rider = userClient.findById(id);
        return matchService.findMatchesByBatchEnd(rider).stream()
                .map(driver -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(driver.getId()).toString())
                .collect(Collectors.toList());
    }

	/**
	 * Gets the liked.
	 *
	 * @param id the id
	 * @return the liked
	 */
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getLiked(@PathVariable("id") int id) {
		List<String> likes = null;
		//TODO: Uncomment to test with real users!
		likes = likeService.getLikes(id).stream()
				.map(like -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(like.getPair().getAffectedId()).toString())
				.collect(Collectors.toList());
//		likes = likeService.getLikes(id).stream()
//				.map(like -> like.toString())
//				.collect(Collectors.toList());
		return likes;
	}

	/**
	 * Adds the liked.
	 *
	 * @param id the id
	 * @param liked the liked
	 */
	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		likeService.saveLike(id,liked);
	}

	/**
	 * Delete liked.
	 *
	 * @param id the id
	 * @param liked the liked
	 */
	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		likeService.deleteLike(id,liked);
	}

	/**
	 * Gets the disliked.
	 *
	 * @param id the id
	 * @return the disliked
	 */
	@RequestMapping(value = "dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDisliked(@PathVariable("id") int id) {
		List<String> dislikes = null;
		//TODO: Uncomment to test with real users!
		dislikes = dislikeService.getDislikes(id).stream()
				.map(dislike -> UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(dislike.getPair().getAffectedId()).toString())
				.collect(Collectors.toList());
//		dislikes = dislikeService.getDislikes(id).stream()
//				.map(dislike -> dislike.toString())
//				.collect(Collectors.toList());
		return dislikes;
	}

	/**
	 * Adds the disliked.
	 *
	 * @param id the id
	 * @param disliked the disliked
	 */
	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		dislikeService.saveDislike(id, disliked);
	}

	/**
	 * Deletedis liked.
	 *
	 * @param id the id
	 * @param disliked the disliked
	 */
	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		dislikeService.deleteDislike(id, disliked);
	}
}
