package com.revature.rideshare.matching.algorithm;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.ImproperRankingReturnedException;

public abstract class RankingCriterion {

	private double weight = 1;
	
	protected abstract double rank(User rider, User driver);
	
	public double getWeightedRank(User rider, User driver) {
		if(rider == null || driver == null) {
			throw new IllegalArgumentException("rider and driver must not be null");
		}
		double driverRanking = rank(rider, driver);
		if(driverRanking < 0 || driverRanking > 1) {
			throw new ImproperRankingReturnedException("Return value from criterion should be between 0 and 1");
		}
		return weight * driverRanking;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	//TODO: Test that two instances of an implementing class are equal
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
			return true;
		return false;
	}

}
