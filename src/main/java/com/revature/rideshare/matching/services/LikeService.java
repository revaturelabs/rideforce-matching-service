package com.revature.rideshare.matching.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.LikeRepository;

@Service
public class LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
	public void saveLike(int id, int liked) {
		Like newLike = new Like(new Pair(id,liked));
		newLike = likeRepository.save(newLike);
	}

	public List<Like> getLikes(int id) {
		List<Like> likes = null;
		likes = likeRepository.findByPairUserId(id);
		return likes;
	}
	
	public void deleteLike(int id, int liked) {
		Like like = new Like(new Pair(id,liked));
		likeRepository.delete(like);
	}
	
}
