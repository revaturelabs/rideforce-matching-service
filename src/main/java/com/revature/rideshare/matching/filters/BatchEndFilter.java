package com.revature.rideshare.matching.filters;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.interfaces.ListFilter;

/**
 * TODO: Javadoc
 * 
 * @author Sanford
 *
 */
public class BatchEndFilter implements ListFilter<User> {

	private User rider;
	private int weekRange;

	public BatchEndFilter(User rider, int weekRange) {
		this.rider = rider;
		this.weekRange = weekRange;
	}

	@Override
	public boolean filter(User e) {
		// ded = driver end date
		Date ded = e.getBatchEnd();
		// red = rider end date
		Date red = rider.getBatchEnd();

		return (ded.equals(DateUtils.addWeeks(red, -weekRange)) || ded.after(DateUtils.addWeeks(red, -weekRange)))
				&& (ded.equals(DateUtils.addWeeks(red, weekRange)) || ded.before(DateUtils.addWeeks(red, weekRange)));
	}

}
