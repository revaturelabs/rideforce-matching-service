package com.revature.rideshare.matching.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;

/**
 * This Class, DislikeService, will provide access to the repository for data retrieval.
 */
@Service
public class DislikeService {
	
	/** The dislike repository. */
	@Autowired
	private DislikeRepository dislikeRepository;
	
	/**
	 * Saves a dislike.
	 *
	 * @param userId the user id
	 * @param disliked the disliked
	 */
	public void saveDislike(int userId, int disliked) {
		Dislike newDislike = new Dislike(new Pair(userId,disliked));
		newDislike = dislikeRepository.save(newDislike);
	}
	
	/**
	 * Gets the dislikes.
	 *
	 * @param id the id
	 * @return the dislikes
	 */
	public List<Dislike> getDislikes(int id) {
		List<Dislike> dislikes = null;
		dislikes = dislikeRepository.findByPairUserId(id);
		return dislikes;
	}
	
	/**
	 * Delete dislike.
	 *
	 * @param id the id
	 * @param disliked the disliked
	 */
	public void deleteDislike(int id, int disliked) {
		Dislike dislike = new Dislike(new Pair(id,disliked));
		dislikeRepository.delete(dislike);
	}

}
