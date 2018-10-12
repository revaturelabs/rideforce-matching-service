package com.revature.rideshare.matching.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.services.DislikeService;
import com.revature.rideshare.matching.services.LikeService;

public class RankByAffect extends RankingCriterion {
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	DislikeService dislikeService;
	
	private List<Integer> likedIds;
	private List<Integer> dislikedIds;
	
	public RankByAffect(int userId) {
		likedIds = likeService.getLikes(userId).stream()
				.map(like -> like.getPair().getAffectedId()).collect(Collectors.toList());
		dislikedIds = dislikeService.getDislikes(userId).stream()
				.map(dislike -> dislike.getPair().getAffectedId()).collect(Collectors.toList());;
	}

	@Override
	public double rank(User rider, User driver) {
		if(dislikedIds.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 0.0;
		} else if(likedIds.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 1.0;
		} else {
			return 0.5;
		}
	}

}
