package com.revature.algorithm.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.revature.rideshare.matching.algorithm.RankByBatchEnd;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.User;

public class RankByBatchEndTest {

	RankByBatchEnd rankByBatchEnd = new RankByBatchEnd();

	User rider = new User();
	User driver1 = new User();
	User driver2 = new User();
	User driver3 = new User();
	User driver4 = new User();
	List<Like> likes = new ArrayList<>();
	List<Dislike> dislikes = new ArrayList<>();
	List<Integer> likedIds = new ArrayList<>();
	List<Integer> dislikedIds = new ArrayList<>();

	{
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);
		driver4.setId(5);

		//TODO: Figure out how to do this without deprecated constructors
//		rider.setBatchEnd(new Date(LocalDate.of(2018, 10, 19).toEpochDay()));
//		driver1.setBatchEnd(new Date(LocalDate.of(2018, 10, 19).toEpochDay()));
//		driver2.setBatchEnd(new Date(LocalDate.of(2018, 11, 1).toEpochDay()));
//		driver3.setBatchEnd(new Date(LocalDate.of(2018, 10, 12).toEpochDay()));
//		driver4.setBatchEnd(new Date(LocalDate.of(2018, 10, 5).toEpochDay()));
		
		rider.setBatchEnd(new Date(2018,10,19));
		driver1.setBatchEnd(new Date(2018,10,19));
		driver2.setBatchEnd(new Date(2018,11,1));
		driver3.setBatchEnd(new Date(2018,10,12));
		driver4.setBatchEnd(new Date(2018,10,5));
		
		System.out.println("Driver3 batchEnd: " + driver3.getBatchEnd());
		System.out.println("Driver4 batchEnd: " + driver4.getBatchEnd());
	}

	@Test
	public void rank_withNullRider_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, driver1);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	@Test
	public void rank_withNullDriver_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(rider, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	@Test
	public void rank_withNullParameters_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	@Test
	public void testRankBatchEndDate_withSameEndDate_returns1() {
		double resultSameEndDate = invokeRank(rider, driver1);

		Assertions.assertThat(resultSameEndDate).isEqualTo(1.0);
	}

	@Test
	public void testRankBatchEndDate_withLaterEndDate_returns1() {
		double resultLaterEndDate = invokeRank(rider, driver2);

		Assertions.assertThat(resultLaterEndDate).isEqualTo(1.0);

	}

	@Test
	public void testRankBatchEndDate_withEarlierEndDate_returnsLessThan1() {
		double resultOneWeekEarlierEndDate = invokeRank(rider, driver3);
		double resultTwoWeeksEarlierEndDate = invokeRank(rider, driver4);

		Assertions.assertThat(resultOneWeekEarlierEndDate).isLessThan(1.0);
		Assertions.assertThat(resultTwoWeeksEarlierEndDate).isLessThan(resultOneWeekEarlierEndDate);

	}

	public double invokeRank(User rider, User driver) throws IllegalArgumentException {
		Method rank = null;
		double result = -1;
		try {
			rank = RankByBatchEnd.class.getDeclaredMethod("rank", User.class, User.class);
			rank.setAccessible(true);
			result = (double) rank.invoke(rankByBatchEnd, rider, driver);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException();
		}

		return result;
	}

}
