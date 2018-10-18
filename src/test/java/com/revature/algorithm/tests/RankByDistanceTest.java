package com.revature.algorithm.tests;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.algorithm.RankByDistance;
import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;

/**
 * The Class RankByDistanceTest.
 */
public class RankByDistanceTest {

	/** The rank by distance. */
	private static RankByDistance rankByDistance;

	/** The test maps client. */
	private static TestMapsClient testMapsClient;

	/** The rider. */
	private static User rider = new User();

	/** The driver. */
	private static User driver = new User();

	/** The route 1. */
	private static Route route1 = new Route();

	/** The route 2. */
	private static Route route2 = new Route();

	/**
	 * Setup.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException   the illegal access exception
	 * @throws NoSuchFieldException     the no such field exception
	 * @throws SecurityException        the security exception
	 */
	@BeforeClass
	public static void setup()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		rider.setId(1);
		driver.setId(2);

		route1.setDistance(100);
		route2.setDistance(200);

		testMapsClient = new TestMapsClient();
		rankByDistance = new RankByDistance();
		Field mapsClient = RankByDistance.class.getDeclaredField("mapsClient");
		mapsClient.setAccessible(true);
		mapsClient.set(rankByDistance, testMapsClient);
	}

	/**
	 * Rank with null rider should throw illegal argument exception.
	 */
	@Test
	public void rank_withNullRider_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, driver);
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
	 * Tests that two drivers at different distances should rank farther drivers
	 * lower.
	 */
	@Test
	public void rank_withTwoDriversAtDifferentDistances_ShouldRankFartherDriversLower() {
		double retrieved1 = invokeRank(rider, driver, route1);
		double retrieved2 = invokeRank(rider, driver, route2);

		Assertions.assertThat(retrieved1).isGreaterThan(retrieved2);
	}

	/**
	 * Invoke rank. Method uses reflection to access private rank method from
	 * RankByDistance.
	 *
	 * @param rider  the rider
	 * @param driver the driver
	 * @return the double
	 */
	public double invokeRank(User rider, User driver) {
		return invokeRank(rider, driver, null);
	}

	/**
	 * Invoke rank. Method uses reflection to access private rank method from
	 * RankByDistance.
	 *
	 * @param rider  the rider
	 * @param driver the driver
	 * @param route  the route
	 * @return the double value representing rider-driver ranking
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public double invokeRank(User rider, User driver, Route route) throws IllegalArgumentException {
		Method rank = null;
		testMapsClient.setRoute(route);
		double result = -1;
		try {
			rank = RankByDistance.class.getDeclaredMethod("rank", User.class, User.class);
			rank.setAccessible(true);
			result = (double) rank.invoke(rankByDistance, rider, driver);
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
