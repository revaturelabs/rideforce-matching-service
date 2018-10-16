package com.revature.rideshare.matching.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.DuplicateRankingCriteriaException;
import com.revature.rideshare.matching.exceptions.NoRankingCriteriaException;

/**
 * Class used to build a complex ranking algorithm. Allows for different ranking
 * criteria to be added to the ranking equation dynamically
 * 
 * @author Ray
 *
 */
public class AggregateRankingBuilder {

	/**
	 * Ranking criteria. Includes each criterion for ranking and its associated
	 * weight.
	 */
	private Set<RankingCriterion> criteria;

	/**
	 * This variable is used to re-scale the ranking to between 0 and 1
	 */
	private double scaleVariable;

	public AggregateRankingBuilder() {
		this.criteria = new HashSet<>();
	}

	/**
	 * Adds a new ranking criterion to the algorithm. It will accept only unique
	 * criterion, repeats will be ignored
	 * 
	 * @param criterion criterion to be added into the algorithm
	 */
	public void addCriterion(RankingCriterion criterion) {
		boolean insertionSuccessful = this.criteria.add(criterion);
		if (insertionSuccessful) {
			this.scaleVariable += criterion.getWeight();
		} else {
			throw new DuplicateRankingCriteriaException(
					"An instance of " + criterion.getClass().getName() + " has already been added to the builder");
		}
	}

	/**
	 * Adds a new ranking criterion to the algorithm with it's associated weight. It
	 * will accept only unique criterion, repeats will be ignored
	 * 
	 * @param criterion criterion to be added into the algorithm
	 * @param weight the weight to be associated with the criterion
	 */
	public void addCriterion(RankingCriterion criterion, double weight) {
		criterion.setWeight(weight);
		boolean insertionSuccessful = this.criteria.add(criterion);
		if (insertionSuccessful) {
			this.scaleVariable += criterion.getWeight();
		} else {
			throw new DuplicateRankingCriteriaException(
					"An instance of " + criterion.getClass().getName() + " has already been added to the builder");
		}
	}

	/**
	 * Ranks a rider and driver pair using the criteria stored in the class
	 * 
	 * @param rider  the rider for whom we are finding a match
	 * @param driver the candidate driver for the match
	 * @return
	 */
	public double rankMatch(User rider, User driver) {
		if (rider == null || driver == null) {
			throw new IllegalArgumentException("rider and driver must not be null");
		}
		if (this.criteria.isEmpty()) {
			throw new NoRankingCriteriaException("At least one criterion required to run algorithm");
		}
		double totalWeightedRank = 0;
		List<Double> weightedRanks = this.criteria.stream().map(criterion -> criterion.getWeightedRank(rider, driver))
				.collect(Collectors.toList());
		for (Double weightedRank : weightedRanks) {
			totalWeightedRank += weightedRank;
		}
		return totalWeightedRank / scaleVariable;
	}

}
