package com.revature.algorithm.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.algorithm.RankByStartTime;
import com.revature.rideshare.matching.beans.User;

/**
 * The Class RankByStartTimeTest.
 */
public class RankByStartTimeTest {

	/** The rank by start time. */
	RankByStartTime rankByStartTime = new RankByStartTime();

	/** Test data objects */
	static User rider = new User();
	static User driver1 = new User();
	static User driver2 = new User();
	static User driver3 = new User();

	/**
	 * Setup to initialize test data.
	 */
	@BeforeClass
	public static void setup() {
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);

		rider.setStartTime(8.0f);
		driver1.setStartTime(8.0f);
		driver2.setStartTime(8.5f);
		driver3.setStartTime(7.5f);
	}

	/**
	 * Tests that rank with null rider throws illegal argument exception.
	 */
	@Test
	public void rank_withNullRider_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, driver1);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	/**
	 * Tests that rank with null driver throws illegal argument exception.
	 */
	@Test
	public void rank_withNullDriver_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(rider, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	/**
	 * Tests that rank with null parameters throws illegal argument exception.
	 */
	@Test
	public void rank_withNullParameters_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}
	
	/**
	 * Tests that driver having same start time returns 1, the highest ranking.
	 */
	@Test
	public void testRankByStartTime_withSameStartTime_returns1() {
		double resultSameStartTime = invokeRank(rider, driver1);
		assertThat(resultSameStartTime).isEqualTo(1.0);
	}
	
	/**
	 * Tests that driver having later start time returns 0, the lowest ranking.
	 */
	@Test
	public void testRankByStartTime_withDriverLaterStartTime_returns0() {
		double resultLaterStartTime = invokeRank(rider, driver2);
		assertThat(resultLaterStartTime).isEqualTo(0);
	}
	
	/**
	 * Tests that driver having earlier start time returns less than 1, the middle ranking.
	 */
	@Test
	public void testRankByStartTime_withDriverEarlierStartTime_returnsLessThan1() {
		double resultEarlierStartTime = invokeRank(rider, driver3);
		assertThat(resultEarlierStartTime).isEqualTo(0.5);
	}
		

	/**
	 * Invoke rank. Method uses reflection to access private rank method from RankByStartTime.
	 *
	 * @param rider the rider
	 * @param the double value representing rider-driver ranking
	 * @return the double
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public double invokeRank(User rider, User driver) throws IllegalArgumentException {
		Method rank = null;
		double result = -1;
		try {
			rank = RankByStartTime.class.getDeclaredMethod("rank", User.class, User.class);
			rank.setAccessible(true);
			result = (double) rank.invoke(rankByStartTime, rider, driver);
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
