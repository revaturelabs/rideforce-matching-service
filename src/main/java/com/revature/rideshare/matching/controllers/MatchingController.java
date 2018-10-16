package com.revature.rideshare.matching.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.matching.beans.Filter;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchingController.class);
	private static final String MSG = "Get request to matching controller made with UserId : %d passed. userClient called to find user by that id. userClient returned the user: %d";
	private static final String NULL = "userClient return a null user object.";
	
	/** This boolean specifies if detailed debugging output should be sent to 
	 * the client or not. True means that the debugging info will be sent. 
	 * DON'T FORGET TO CHANGE TO FALSE BEFORE PUSH TO PRODUCTION! */
	private static final boolean DEBUG = true;
	
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
	 * Gets all matched drivers to riders using rider's ID as input and driver's ID
	 * to get drivers.
	 *
	 * @param id the rider's ID
	 * @return list of matched drivers by their IDs
	 */
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
	
	//TODO: Implement endpoint
	@RequestMapping(value = "/filtered", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllFiltered(@RequestBody Filter filter, @RequestBody int id) {
		return null;
	}

	/**
	 * Gets matched drivers to riders taking rider ID as input and explicitly
	 * excluding liked and disliked drivers, gotten by driver ID.
	 * 
	 * @param id the rider id
	 * @return matched drivers minus liked and disliked drivers
	 */
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

	/**
	 * Gets matched drivers to riders by distance, taking rider ID as input and
	 * using driver ID to get drivers.
	 *
	 * @param id the rider ID
	 * @return list of matched drivers by distance to rider
	 */
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

	/**
	 * Gets matched drivers to riders by batch end date, taking rider ID as input
	 * and using driver ID to get drivers.
	 *
	 * @param id the rider id
	 * @return list of matched drivers by batch end date same as or after rider's
	 */
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

	/**
	 * Gets matched drivers by liked affect, using the rider ID as input and the
	 * drivers' IDs to get drivers.
	 *
	 * @param id the rider ID
	 * @return list of matched drivers who have been liked by rider
	 */
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getLiked(@PathVariable("id") int id) {
		List<String> likes = null;

		likes = likeService.getLikes(id).stream().map(like -> UriComponentsBuilder.fromPath("/users/{id}")
				.buildAndExpand(like.getPair().getAffectedId()).toString()).collect(Collectors.toList());
		if (likes.isEmpty()) {
			LOGGER.trace("Mapping process did not return any URIs associated with this user id: %d", id);
		} else {
			LOGGER.info(
					"likeService.getLikes called with id: %d which is then mapped to create a list of uri's that contain a path to "
							+ "to get the users (drivers) that they have liked.",
					id);
		}
		return likes;
	}

	/**
	 * Saves like affect to a user, taking the ID of the liker as input.
	 *
	 * @param id    the ID of user taking the action of liking
	 * @param liked the ID of the liked user
	 */
	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		LOGGER.info("Like service called to save a like for the userId %d and affected userId %d.", id, liked);
		likeService.saveLike(id, liked);
	}

	/**
	 * Deletes like affect from a user, taking the ID of the liker as input.
	 *
	 * @param id    the ID of the user taking the action of deleting like
	 * @param liked the ID of liked user
	 */
	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.DELETE)
	public void deleteLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		LOGGER.info("Like service called to delete a like for the userId %d and affected userId %d.", id, liked);
		likeService.deleteLike(id, liked);
	}

	/**
	 * Gets matched users by dislike affect, taking ID of the disliker as input.
	 *
	 * @param id the ID of the user taking the action of disliking
	 * @return list of disliked users associated with input user ID
	 */
	@RequestMapping(value = "dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDisliked(@PathVariable("id") int id) {
		List<String> dislikes = null;
		dislikes = dislikeService.getDislikes(id).stream().map(dislike -> UriComponentsBuilder.fromPath("/users/{id}")
				.buildAndExpand(dislike.getPair().getAffectedId()).toString()).collect(Collectors.toList());
		if (dislikes.isEmpty()) {
			LOGGER.trace("Mapping process did not return any URIs associated with this user id: %d ", id);
		} else {
			LOGGER.info(
					"dislikeService.getDislikes called with id: %d which was then mapped to create a list of uri's that contain a path to "
							+ "to get the users (drivers) that they have liked.",
					id);
		}

		return dislikes;
	}

	/**
	 * Adds disliked affect to a user, taking ID of the disliker as input.
	 *
	 * @param id       the ID of user taking the action of disliking
	 * @param disliked the ID of the disliked user
	 */
	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		LOGGER.info("Dislike service called to save a dislike for the userId %d and affected userId %d.", id, disliked);
		dislikeService.saveDislike(id, disliked);
	}

	/**
	 * Deletes dislike affect from affected user, taking disliker ID as input.
	 *
	 * @param id       the ID of user taking action of deleting dislike
	 * @param disliked the ID of the disliked user
	 */
	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.DELETE)
	public void deletedisLiked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		LOGGER.info("Dislike service called to delete a dislike for the userId %d and affected userId %d.", id, disliked);
		dislikeService.deleteDislike(id, disliked);
	}
	
	
	/**
	 * This is the general exception handler. This handles any exceptions that 
	 * may occur in this controller. 
	 * @param request - The request associated with the exception.
	 * @param ex - The exception that was thrown
	 * @return - A ResponseEntity that has information about the exception. 
	 */
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<String> handleError(HttpServletRequest request, Exception ex) {
		// Construct an error message to send back to log. 
		String message = "Request: \"%s\" With Query Params: \"%s\" threw Exception: %s";
		
		// Log the error with the provided information. 
		LOGGER.error(message, request.getRequestURL(), request.getQueryString(), ex);
		LOGGER.error("Stack Trace: ", ex);
		
		// If debugging is enabled, return a detailed string message.
		if (DEBUG) {
			return new ResponseEntity<>(generateStackTrace(ex), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Otherwise, just send the status with no meaningful message. 
		else {
			return new ResponseEntity<>("An error occurred processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/** 
	 * This is a helper method that generates a stack trace as a string. 
	 * @param t - the {@code throwable} that to get the trace of. 
	 * @return A single string representation of the stack trace. 
	 */
	private String generateStackTrace(Throwable t) {
		// Create writers that the throwable can write to. 
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		// Print the generated stack trace to a string
		t.printStackTrace(printWriter);
		printWriter.flush();
		String stackTrace = stringWriter.toString();
		
		// Closing here is not necessary, but it is done out of good practice 
		// anyway.
		printWriter.close();
		
		return stackTrace;
	}
	
}
