package com.revature.rideshare.matching.algorithm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.criterion.Criterion;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.NoRankingCriteriaException;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

public class AggregateRankingBuilder {

	/**
	 * Ranking criteria. Includes each criterion for ranking and its associated
	 * weight.
	 */
	private Set<RankingCriterion> criteria;

	/**
	 * This variable is used to rescale the ranking to between 0 and 1
	 */
	private double scaleVariable;

	public AggregateRankingBuilder() {
	}

	/**
	 * Adds a new ranking criterion to the algorithm. It will accept only unique
	 * criterion, repeats will be ignored
	 * 
	 * @param criterion criterion to be added into the algorithm
	 */
	public void addCriterion(RankingCriterion criterion) {
		this.criteria.add(criterion);
		this.scaleVariable += criterion.getWeight();
	}

	// TODO: Implement ranking algorithm
	public double rankMatch(User rider, User driver) {
		if(rider == null || driver == null) {
			throw new IllegalArgumentException("rider and driver must not be null");
		}
		if(this.criteria.size() == 0) {
			throw new NoRankingCriteriaException("At least one criterion required to run algorithm");
		}
		double totalWeightedRank = 0;
		List<Double> weightedRanks = this.criteria.stream().map(criteria -> criteria.getWeightedRank(rider, driver)).collect(Collectors.toList());
		for(Double weightedRank: weightedRanks) {
			totalWeightedRank += weightedRank;
		}
		return totalWeightedRank;
	}

}
