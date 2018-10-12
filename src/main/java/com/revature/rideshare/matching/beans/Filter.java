package com.revature.rideshare.matching.beans;
/**
 * This class is used in the matching algorithm. It is used to determine which
 * criteria are used when ranking matches
 * 
 * @author Ray
 *
 */
public class Filter {

	private boolean batchEndChange;
	private boolean dayStartChange;
	private boolean distanceChange;

	public Filter() {
	}

	public Filter(boolean batchEndChange, boolean dayStartChange, boolean distanceChange) {
		super();
		this.batchEndChange = batchEndChange;
		this.dayStartChange = dayStartChange;
		this.distanceChange = distanceChange;
	}

	public boolean isBatchEndChange() {
		return batchEndChange;
	}

	public void setBatchEndChange(boolean batchEndChange) {
		this.batchEndChange = batchEndChange;
	}

	public boolean isDayStartChange() {
		return dayStartChange;
	}

	public void setDayStartChange(boolean dayStartChange) {
		this.dayStartChange = dayStartChange;
	}

	public boolean isDistanceChange() {
		return distanceChange;
	}

	public void setDistanceChange(boolean distanceChange) {
		this.distanceChange = distanceChange;
	}

}
