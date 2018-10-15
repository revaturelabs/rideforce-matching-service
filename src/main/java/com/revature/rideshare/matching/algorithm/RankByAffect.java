package com.revature.rideshare.matching.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.services.DislikeService;
import com.revature.rideshare.matching.services.LikeService;

/**
 * Class used to rank a rider driver pair by affect
 * 
 * @author Ray
 *
 */
public class RankByAffect extends RankingCriterion {
	
	/**
	 * The service used to get the likes a rider has made
	 */
	@Autowired
	LikeService likeService;
	
	/**
	 * The service used to get the dislikes a rider has made
	 */
	@Autowired
	DislikeService dislikeService;
	
	/**
	 * The list of IDs of drivers whom the rider has liked and disliked
	 */
	private List<Integer> likedIds;
	private List<Integer> dislikedIds;
	
	/**
	 * Function used to populate the likedIds and dislikedIds. Runs the first time the rank algorithm is called
	 * @param rider
	 */
	private void initializeAffects(User rider) {
		likedIds = likeService.getLikes(rider.getId()).stream()
				.map(like -> like.getPair().getAffectedId()).collect(Collectors.toList());
		dislikedIds = dislikeService.getDislikes(rider.getId()).stream()
				.map(dislike -> dislike.getPair().getAffectedId()).collect(Collectors.toList());;
	}

	/**
	 * Ranks a driver based on whether they have been liked or disliked
	 * 
	 * @param rider the rider under consideration
	 * @param driver the potential driver who we are ranking
	 * @return
	 */
	@Override
	protected double rank(User rider, User driver) {
		if(likedIds == null || dislikedIds == null) {
			initializeAffects(rider);
		}
		if(dislikedIds.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 0.0;
		} else if(likedIds.stream().anyMatch(id -> id.equals(driver.getId()))) {
			return 1.0;
		} else {
			return 0.5;
		}
	}

}
