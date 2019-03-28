package com.revature.rideshare.matching.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/matches")
public class MatchingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchingController.class);
	private static final String MSG = "Get request to matching controller made with UserId : {} passed. userClient called to find user by that id. userClient returned the user: {}";
	private static final String NULL = "userClient returned a null user object.";
	private static final String USER_ID_URI = "/users/{id}";

	/**
	 * This boolean specifies if detailed debugging output should be sent to the
	 * client or not. True means that the debugging info will be sent. DON'T FORGET
	 * TO CHANGE TO FALSE BEFORE PUSH TO PRODUCTION!
	 */
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
	 * This method is not intended to be used in production please comment out if
	 * you see this. This method was used to see what we were getting from the feign
	 * client
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<User>> getAllDrivers() {
		if (!DEBUG) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(matchService.findAll(), HttpStatus.OK);
	}

	/**
	 * Gets all matched drivers to riders using rider's ID as input and driver's ID
	 * to get drivers.
	 *
	 * @param id the rider's ID
	 * @return list of matched drivers by their IDs
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getDrivers(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		LOGGER.info("Token: {}", authToken);
		LOGGER.info("getDrivers() for UserId: " + id);

		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.error(NULL);
			return new ArrayList<>();
		}

		LOGGER.info(MSG, id, rider.getFirstName());
		List<String> matches = matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
		LOGGER.debug("Returning matches: " + matches);
		return matches;
	}

	/**
	 * Gets all matched drivers to riders using rider's ID as input and driver's ID
	 * to get drivers.
	 *
	 * @param id the rider's ID
	 * @return list of matched drivers by their IDs
	 */
	@Deprecated
	@RequestMapping(value = "/old/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAll(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		LOGGER.info(authToken);
		LOGGER.info("getAll() for UserId: " + id);

		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.error(NULL);
			return new ArrayList<>();
		}

		LOGGER.info(MSG, id, rider.getFirstName());
		List<String> matches = matchService.findMatches(rider).stream()
				.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
				.collect(Collectors.toList());
		LOGGER.debug("Returning matches: " + matches.toString());
		return matches;
	}

	/**
	 * Gets all matched drivers using criteria specified in the filter object.
	 * 
	 * @param filter the filter used to determine which criteria to add to the
	 *               algorithm
	 * @param id     the id of the rider for whom we determine matches
	 * @return a list of drivers which are matched to the rider
	 */
	@RequestMapping(value = "/filtered/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllFiltered(@RequestBody Filter filter, @PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.trace(NULL);
			return new ArrayList<>();
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
			return matchService.findFilteredMatches(filter, rider).stream()
					.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
					.collect(Collectors.toList());
		}
	}

	/**
	 * Gets matched drivers to riders taking rider ID as input and explicitly
	 * excluding liked and disliked drivers, gotten by driver ID.
	 * 
	 * @param id the rider id
	 * @return matched drivers minus liked and disliked drivers
	 */
	@RequestMapping(value = "/likes-dislikes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllMinusAffects(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.error(NULL);
			return new ArrayList<>();
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
			return matchService.findMatchesByAffects(rider).stream()
					.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
					.collect(Collectors.toList());
		}

	}

	/**
	 * Gets matched drivers to riders by distance, taking rider ID as input and
	 * using driver ID to get drivers.
	 *
	 * @param id the rider ID
	 * @return list of matched drivers by distance to rider
	 */
	@RequestMapping(value = "/distance/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByDistance(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.error(NULL);
			return new ArrayList<>();
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
			return matchService.findMatchesByDistance(rider).stream()
					.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
					.collect(Collectors.toList());
		}

	}

	/**
	 * Gets matched drivers to riders by batch end date, taking rider ID as input
	 * and using driver ID to get drivers.
	 *
	 * @param id the rider id
	 * @return list of matched drivers by batch end date same as or after rider's
	 */
	@RequestMapping(value = "/batch-end/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByBatchEnd(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.error(NULL);
			return new ArrayList<>();
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
			return matchService.findMatchesByBatchEnd(rider).stream()
					.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
					.collect(Collectors.toList());
		}

	}

	@RequestMapping(value = "/start-time/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getByStartTime(@PathVariable int id, HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		User rider = userClient.findById(id, authToken);
		if (rider == null) {
			LOGGER.trace(NULL);
			return new ArrayList<>();
		} else {
			LOGGER.info(MSG, id, rider.getFirstName());
			return matchService.findMatchesByStartTime(rider).stream()
					.map(driver -> UriComponentsBuilder.fromPath(USER_ID_URI).buildAndExpand(driver.getId()).toString())
					.collect(Collectors.toList());
		}

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

		likes = likeService.getLikes(id).stream().map(like -> UriComponentsBuilder.fromPath(USER_ID_URI)
				.buildAndExpand(like.getPair().getAffectedId()).toString()).collect(Collectors.toList());
		if (likes == null) {
			LOGGER.error("Mapping process returned null");
			return new ArrayList<>();
		} else if (likes.isEmpty()) {
			LOGGER.error("Mapping process did not return any URIs associated with this user id: {}", id);
			return likes;
		} else {
			LOGGER.info(
					"likeService.getLikes called with id: {} which is then mapped to create a list of uri's that contain a path to "
							+ "to get the users (drivers) that they have liked.",
					id);
			return likes;
		}
	}

	/**
	 * Saves like affect to a user, taking the ID of the liker as input.
	 *
	 * @param id    the ID of user taking the action of liking
	 * @param liked the ID of the liked user
	 */
	@RequestMapping(value = "/likes/{id}/{liked}", method = RequestMethod.PUT)
	public void addLiked(@PathVariable("id") int id, @PathVariable("liked") int liked) {
		LOGGER.info("Like service called to save a like for the userId {}and affected userId {}.", id, liked);
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
		LOGGER.info("Like service called to delete a like for the userId {} and affected userId {}.", id, liked);
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
		dislikes = dislikeService.getDislikes(id).stream().map(dislike -> UriComponentsBuilder.fromPath(USER_ID_URI)
				.buildAndExpand(dislike.getPair().getAffectedId()).toString()).collect(Collectors.toList());
		if (dislikes == null) {
			return new ArrayList<>();
		} else if (dislikes.isEmpty()) {
			LOGGER.error("Mapping process did not return any URIs associated with this user id: {} ", id);
			return dislikes;
		} else {
			LOGGER.info(
					"dislikeService.getDislikes called with id: {} which was then mapped to create a list of uri's that contain a path to "
							+ "to get the users (drivers) that they have liked.",
					id);
			return dislikes;
		}
	}

	/**
	 * Adds disliked affect to a user, taking ID of the disliker as input.
	 *
	 * @param id       the ID of user taking the action of disliking
	 * @param disliked the ID of the disliked user
	 */
	@RequestMapping(value = "/dislikes/{id}/{disliked}", method = RequestMethod.PUT)
	public void addDisliked(@PathVariable("id") int id, @PathVariable("disliked") int disliked) {
		LOGGER.info("Dislike service called to save a dislike for the userId {} and affected userId {}.", id, disliked);
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
		LOGGER.info("Dislike service called to delete a dislike for the userId {} and affected userId {}.", id,
				disliked);
		dislikeService.deleteDislike(id, disliked);
	}

	/**
	 * This is the general exception handler. This handles any exceptions that may
	 * occur in this controller.
	 * 
	 * @param request - The request associated with the exception.
	 * @param ex      - The exception that was thrown
	 * @return - A ResponseEntity that has information about the exception.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleError(HttpServletRequest request, Exception ex) {
		String message = "Request: \"{}\" With Query Params: \"{}\" threw Exception: {}";

		LOGGER.error(message, request.getRequestURL(), request.getQueryString(), ex);
		LOGGER.error("Stack Trace: ", ex);

		if (DEBUG) {
			return new ResponseEntity<>(generateStackTrace(ex, false), HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>("An error occurred processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This is a helper method that generates a stack trace as a string.
	 * 
	 * @param t           - the {@code throwable} that to get the trace of.
	 * @param webFriendly - Specifies that new line characters should be replaced
	 *                    line breaks if true.
	 * @return A single string representation of the stack trace.
	 */
	private String generateStackTrace(Throwable t, boolean webFriendly) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		t.printStackTrace(printWriter);
		printWriter.flush();
		String stackTrace = stringWriter.toString();

		printWriter.close();

		if (webFriendly) {
			stackTrace = stackTrace.replaceAll("\n", "<br>");
		}

		return stackTrace;
	}

}
