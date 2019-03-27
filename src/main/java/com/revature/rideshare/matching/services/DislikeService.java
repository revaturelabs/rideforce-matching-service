package com.revature.rideshare.matching.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;

/**
 * This Class, DislikeService, will provide access to the repository for data
 * retrieval to get data referring to the affection relationship between users.
 */
@Service
public class DislikeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DislikeService.class);
	/** The dislike repository. */
	@Autowired
	private DislikeRepository dislikeRepository;

	/**
	 * Saves a new dislike.
	 *
	 * @param userId   the user id
	 * @param disliked the disliked
	 */
	public void saveDislike(int userId, int disliked) {
		LOGGER.info("Dislike being saved to database.");
		Dislike newDislike = new Dislike(new Pair(userId, disliked));
		Dislike savedDislike = dislikeRepository.save(newDislike);
		if (!savedDislike.equals(newDislike)) {
			LOGGER.error(
					"Error saving dislike to database. Dislike returned from save does not match the dislike passed.");
		}
	}

	/**
	 * Gets the dislikes associated with the user id passed. These will be the the
	 * dislikes owned by the user id passed.
	 *
	 * @param id the id
	 * @return the dislikes
	 */
	public List<Dislike> getDislikes(int userId) {
		LOGGER.info("retrieving dislikes for userId: {}", userId);
		List<Dislike> dislikes = null;
		dislikes = dislikeRepository.findByPairUserId(userId);
		return dislikes;
	}

	/**
	 * Deletes dislike by the user id of both users associated by the dislike.
	 *
	 * @param userId   the user taking action of deleting dislike
	 * @param disliked the disliked user
	 */
	public void deleteDislike(int userId, int disliked) {
		Dislike dislike = new Dislike(new Pair(userId, disliked));
		LOGGER.info("Deleting like with userId: {}, and affected userId: {}.", userId, disliked);
		dislikeRepository.delete(dislike);
	}

}
