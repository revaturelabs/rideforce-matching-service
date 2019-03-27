package com.revature.rideshare.matching.comparators;

import java.util.Comparator;
import java.util.Date;

import com.revature.rideshare.matching.beans.User;

public class BatchEndComparator implements Comparator<User> {

	private Date riderEnd;

	public BatchEndComparator(User rider) {
		this.riderEnd = rider.getBatchEnd();
	}

	@Override
	public int compare(User driver1, User driver2) {
		long u = riderEnd.getTime();
		long d1 = driver1.getBatchEnd().getTime() - u;
		long d2 = driver2.getBatchEnd().getTime() - u;
		return (d1 > d2) ? 1 : (d1 < d2 ? -1 : 0);
	}

}
