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

public class RankByDistanceTest {
	
	private static RankByDistance rankByDistance;
	private static TestMapsClient testMapsClient;
	
	private static User rider = new User();
	private static User driver = new User();
	
	private static Route route1 = new Route();
	private static Route route2 = new Route();
	
	@BeforeClass
	public static void setup() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException	{
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
	
	@Test
	public void rank_withNullRider_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, driver);
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
	public void rank_withTwoDriversAtDifferentDistances_ShouldRankFartherDriversLower() {
		double retrieved1 = invokeRank(rider,driver, route1);
		double retrieved2 = invokeRank(rider,driver, route2);
		
		Assertions.assertThat(retrieved1).isGreaterThan(retrieved2);
	}
	
	public double invokeRank(User rider, User driver) {
		return invokeRank(rider, driver, null);
	}
	
	public double invokeRank(User rider, User driver, Route route) throws IllegalArgumentException {
		Method rank = null;
//		TestMapsClient testMapsClient = new TestMapsClient();
		testMapsClient.setRoute(route);
//		RankByDistance rankByDistance = new RankByDistance(testMapsClient);
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
