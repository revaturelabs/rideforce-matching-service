package com.revature.rideshare.matching.beans;

import org.springframework.stereotype.Component;

/** 
 * This object used to store the computed distance and duration of a drive 
 * between two addresses, as computed by the google maps API. 
 * 
 * Currently, according to the {@code RouteService} in the {@code map-service} 
 * project, the units are in meters for distance, and seconds for duration. 
 */
@Component
public class Route {
	
	/** The distance between the source address and the destination address.
	 * <br><br>
	 * The current implementation according to 
	 * the {@code RouteService} in the {@code map-service} project defines 
	 * this in meters.  */
	long distance;
	
	/** The duration of the drive, according to google maps, between the source
	 * address and destination address. 
	 * <br><br>
	 * The current implementation according to 
	 * the {@code RouteService} in the {@code map-service} project defines 
	 * this in seconds. */
	long duration;
	
	/**  
	 * Constructs a new Route object with all of it's parameters uninitialized.
	 */
	public Route() {
	}
	
	/**  
	 * Constructs a new Route object. 
	 * 
	 * @param distance - The distance between the source address and destination
	 * 					address.
	 * @param duration - The duration of the drive between the source address 
	 * 					and destination address.
	 */
	public Route(long distance, long duration) {
		this.distance = distance;
		this.duration = duration;
	}

	/**
	 * Returns the distance between the source address and destination address 
	 * as computed by google maps.
	 * @return distance between addresses
	 */
	public long getDistance() {
		return distance;
	}

	/**
	 * Sets the distance between the source address and destination address.
	 * @param distance between addresses
	 */
	public void setDistance(long distance) {
		this.distance = distance;
	}

	/**
	 * Returns the duration of the drive between the source address and 
	 * destination address as computed by google maps.
	 * @return duration of drive between addresses
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the drive between the source address and destination 
	 * address.
	 * @param duration of drive between addresses
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	
	/** Returns a hashcode for this Route object. This code is based on the 
	 * distance and duration. */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (distance ^ (distance >>> 32));
		result = prime * result + (int) (duration ^ (duration >>> 32));
		return result;
	}

	/** Tests for equality between this {@code Route} and another object. This 
	 * returns true if the provided object reference is a non-null reference to 
	 * a {@code Route}  object, and that the distances and durations of this 
	 * and the other {@code Route} objects are equal. Returns false otherwise. 
	 * @param obj - An object to test for equality against
	 * @return True if this and {@code obj} are equal. False otherwise. */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (distance != other.distance)
			return false;
		if (duration != other.duration)
			return false;
		return true;
	}

	/**
	 * Returns a string representation of this object. 
	 * @return A string representation of this object. 
	 */
	@Override
	public String toString() {
		return "Route [distance=" + distance + ", duration=" + duration + "]";
	}

}
