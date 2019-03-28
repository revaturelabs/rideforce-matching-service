package com.revature.rideshare.matching.filters;

import org.apache.commons.lang.time.DateUtils;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;

public class BatchEndFilter implements ListFilter<User> {

	private User rider;
	private int weekRange;

	public BatchEndFilter(User rider, int weekRange) {
		this.rider = rider;
		this.weekRange = weekRange;
	}

	@Override
	public boolean filter(User e) {
		return e.getBatchEnd().after(DateUtils.addWeeks(rider.getBatchEnd(), -weekRange))
				&& e.getBatchEnd().before(DateUtils.addWeeks(rider.getBatchEnd(), weekRange));
	}

}
