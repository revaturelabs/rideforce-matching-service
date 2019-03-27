package com.revature.rideshare.matching.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.LikeRepository;

/**
 * The Class LikeService.
 */
@Service
public class LikeService {

	/** The like repository. */
	@Autowired
	LikeRepository likeRepository;

	/**
	 * Save like.
	 *
	 * @param userId the userId
	 * @param liked  the affectedId
	 */
	public void saveLike(int userId, int liked) {
		Like newLike = new Like(new Pair(userId, liked));
		likeRepository.save(newLike);
	}

	/**
	 * Gets the likes.
	 *
	 * @param userId the userId
	 * @return the likes
	 */
	public List<Like> getLikes(int userId) {
		List<Like> likes = null;
		likes = likeRepository.findByPairUserId(userId);
		return likes;
	}

	/**
	 * Delete like.
	 *
	 * @param userId the userId
	 * @param liked  the affectedId
	 */
	public void deleteLike(int userId, int liked) {
		Like like = new Like(new Pair(userId, liked));
		likeRepository.delete(like);
	}
}
