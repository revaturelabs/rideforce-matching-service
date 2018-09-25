package com.revature.rideshare.matching.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;

@Service
public class DislikeService {
	
	@Autowired
	private DislikeRepository dislikeRepository;
	
	public void saveDislike(int id, int disliked) {
		Dislike newDislike = new Dislike(new Pair(id,disliked));
		newDislike = dislikeRepository.save(newDislike);
	}
	
	public List<Dislike> getDislikes(int id) {
		List<Dislike> dislikes = null;
		dislikes = dislikeRepository.findByPairUserId(id);
		return dislikes;
	}
	
	public void deleteDislike(int id, int disliked) {
		Dislike dislike = new Dislike(new Pair(id,disliked));
		dislikeRepository.delete(dislike);
	}

}
