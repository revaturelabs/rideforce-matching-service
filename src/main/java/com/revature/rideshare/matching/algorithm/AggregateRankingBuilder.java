package com.revature.rideshare.matching.algorithm;

import java.util.List;

import com.revature.rideshare.matching.beans.User;

public class AggregateRankingBuilder {
	
	private List<RankingCriterion> criteria;
	
	public AggregateRankingBuilder() {}
	
	public void addCriteria(RankingCriterion criterion) {
		this.criteria.add(criterion);
	}
	
	public double rank(User rider, List<User> drivers) {
		return 0;
	}

}
