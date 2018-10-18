package com.revature.algorithm.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.algorithm.RankByBatchEnd;
import com.revature.rideshare.matching.beans.User;

/**
 * The Class RankByBatchEndTest.
 */
public class RankByBatchEndTest {

	/** Class being tested */
	RankByBatchEnd rankByBatchEnd = new RankByBatchEnd();

	/** Test data objects */
	static User rider = new User();
	static User driver1 = new User();
	static User driver2 = new User();
	static User driver3 = new User();
	static User driver4 = new User();

	/**
	 * Setup to initialize test data.
	 */
	@BeforeClass
	public static void setup() {
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);
		driver4.setId(5);

		rider.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver1.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver2.setBatchEnd(new Date(Instant.parse("2018-11-01T00:00:00Z").toEpochMilli()));
		driver3.setBatchEnd(new Date(Instant.parse("2018-10-12T00:00:00Z").toEpochMilli()));
		driver4.setBatchEnd(new Date(Instant.parse("2018-10-05T00:00:00Z").toEpochMilli()));

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
	 * Tests that driver having same batch end date returns 1, the highest ranking.
	 */
	@Test
	public void testRankBatchEndDate_withSameEndDate_returns1() {
		double resultSameEndDate = invokeRank(rider, driver1);

		Assertions.assertThat(resultSameEndDate).isEqualTo(1.0);
	}

	/**
	 * Tests that driver having a later batch end date returns 1, the highest
	 * ranking.
	 */
	@Test
	public void testRankBatchEndDate_withLaterEndDate_returns1() {
		double resultLaterEndDate = invokeRank(rider, driver2);

		Assertions.assertThat(resultLaterEndDate).isEqualTo(1.0);

	}

	/**
	 * Tests that driver having earlier batch end date returns less than 1, a lower
	 * ranking.
	 */
	@Test
	public void testRankBatchEndDate_withEarlierEndDate_returnsLessThan1() {
		double resultOneWeekEarlierEndDate = invokeRank(rider, driver3);
		double resultTwoWeeksEarlierEndDate = invokeRank(rider, driver4);

		Assertions.assertThat(resultOneWeekEarlierEndDate).isLessThan(1.0);
		Assertions.assertThat(resultTwoWeeksEarlierEndDate).isLessThan(resultOneWeekEarlierEndDate);

	}

	/**
	 * Invoke rank. Method uses reflection to access private rank method from
	 * RankByBatchEnd.
	 *
	 * @param rider  the rider
	 * @param driver the driver
	 * @return the double value representing rider-driver ranking
	 * @throws IllegalArgumentException the illegal argument exception
	 */
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
