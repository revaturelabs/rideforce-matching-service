package com.revature.rideshare.matching.algorithm;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.ImproperRankingReturnedException;

/**
 * Abstract class used to represent a criterion for which we will perform some
 * weighted ranking.
 * 
 * @author Ray
 *
 */
public abstract class RankingCriterion {

	/**
	 * Weight applied to the ranking
	 */
	private double weight = 1;

	/**
	 * Method used to perform a simple ranking algorithm defined in the child class
	 * 
	 * @param rider  the rider for whom we are finding a suitable driver
	 * @param driver the driver whose suitability is being measured
	 * @return a double between 0 and 1 representing the rank of the rider-driver
	 *         pairing
	 */
	protected abstract double rank(User rider, User driver);

	/**
	 * Method used to form a ranking which is weighted using the weight field.
	 * 
	 * @param rider  the rider for whom we are finding a suitable driver
	 * @param driver the driver who's suitability is being measured
	 * @return a double between 0 and 1 representing the rank of the rider-driver
	 *         pairing
	 */
	public double getWeightedRank(User rider, User driver) {
		if (rider == null || driver == null) {
			throw new IllegalArgumentException("Rider and driver must not be null");
		}
		double driverRanking = rank(rider, driver);
		if (driverRanking < 0 || driverRanking > 1) {
			throw new ImproperRankingReturnedException("Return value from criterion should be between 0 and 1");
		}
		return weight * driverRanking;
	}

	/**
	 * Method used to get the weight
	 * 
	 * @return the weight applied to the ranking
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Method used to set the weight
	 * 
	 * @param weight the weight which we will apply to the ranking
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Used to allow different children classes to have different hashes if they have the same weight
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Integer.toUnsignedLong(getClass().getName().hashCode());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Used to constrain that all instances of a child class are viewed as equal.
	 * This allows for only unique criterion to be stored as set items.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return getClass() == obj.getClass();
	}

}
