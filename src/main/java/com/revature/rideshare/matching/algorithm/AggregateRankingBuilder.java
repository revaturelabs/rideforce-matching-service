package com.revature.rideshare.matching.algorithm;

import java.util.List;

public class AggregateRankingBuilder {
	
	private List<RankingCriterion> criteria;
	
	public AggregateRankingBuilder() {}

	public List<RankingCriterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<RankingCriterion> criteria) {
		this.criteria = criteria;
	}
	
	public void addCriteria(RankingCriterion criterion) {
		this.criteria.add(criterion);
	}

}
