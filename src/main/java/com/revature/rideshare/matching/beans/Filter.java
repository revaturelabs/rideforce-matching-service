package com.revature.rideshare.matching.beans;
// TODO: Auto-generated Javadoc
/**
 * This class is used in the matching algorithm. It is used to determine which
 * criteria are used when ranking matches.
 * 
 * @author Ray
 *
 */
public class Filter {

	/** Sets whether batch end is weighted in ranking. */
	private boolean batchEndChange;
	
	/** Sets whether daily start time is weighted in ranking. */
	private boolean dayStartChange;
	
	/** Sets whether distance is weighted in ranking. */
	private boolean distanceChange;

	/**
	 * Instantiates a new filter.
	 */
	public Filter() {
		this.batchEndChange = false;
		this.dayStartChange = false;
		this.distanceChange = false;
	}

	/**
	 * Instantiates a new filter.
	 *
	 * @param batchEndChange whether batch end date weighting is considered in ranking
	 * @param dayStartChange whether daily start time weighting is considered in ranking
	 * @param distanceChange whether distance weighting is considered in ranking
	 */
	public Filter(boolean batchEndChange, boolean dayStartChange, boolean distanceChange) {
		super();
		this.batchEndChange = batchEndChange;
		this.dayStartChange = dayStartChange;
		this.distanceChange = distanceChange;
	}

	/**
	 * Checks if batch end weighting is disregarded or not.
	 *
	 * @return true, if batch end weight is included in ranking
	 */
	public boolean isBatchEndChange() {
		return batchEndChange;
	}

	/**
	 * Sets the batch end change.
	 *
	 * @param batchEndChange whether batch end date weighting is considered in ranking
	 */
	public void setBatchEndChange(boolean batchEndChange) {
		this.batchEndChange = batchEndChange;
	}

	/**
	 * Checks if daily start time is disregarded in ranking or not.
	 *
	 * @return true, if daily start time is included in ranking
	 */
	public boolean isDayStartChange() {
		return dayStartChange;
	}

	/**
	 * Sets the day start change.
	 *
	 * @param dayStartChange whether daily start time weighting is considered in ranking
	 */
	public void setDayStartChange(boolean dayStartChange) {
		this.dayStartChange = dayStartChange;
	}

	/**
	 * Checks if distance is disregarded in ranking or not.
	 *
	 * @return true, if distance is included in ranking
	 */
	public boolean isDistanceChange() {
		return distanceChange;
	}

	/**
	 * Sets the distance change.
	 *
	 * @param distanceChange whether distance weighting is considered in ranking
	 */
	public void setDistanceChange(boolean distanceChange) {
		this.distanceChange = distanceChange;
	}

}
