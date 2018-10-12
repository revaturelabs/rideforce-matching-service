package com.revature.rideshare.matching.algorithm;

import com.revature.rideshare.matching.beans.User;

public abstract class RankingCriterion {

	private double weight = 1;
	
	public abstract double rank(User rider, User driver);

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
