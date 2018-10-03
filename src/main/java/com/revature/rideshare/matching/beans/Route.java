package com.revature.rideshare.matching.beans;

import org.springframework.stereotype.Component;

@Component
public class Route {
	long distance;
	long duration;

	public Route() {
	}

	public Route(long distance, long duration) {
		this.distance = distance;
		this.duration = duration;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (distance ^ (distance >>> 32));
		result = prime * result + (int) (duration ^ (duration >>> 32));
		return result;
	}

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

	@Override
	public String toString() {
		return "Route [distance=" + distance + ", duration=" + duration + "]";
	}

}
