package com.revature.bean.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Route;
import com.revature.rideshare.matching.beans.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteBeanTest.
 */
public class RouteBeanTest {

	/**
	 * The local validator factory. This is used to make sure that certain annotated
	 * constraints are or aren't violated.
	 * 
	 * As the Route class currently doesn't have any annotations besides Component
	 */
	private static LocalValidatorFactoryBean localValidatorFactory;

	/** Sets up the validator factory for the tests. */
	@BeforeClass
	public static void setupValidatorFactory() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	/** test Get Distance method in Route Class. */
	@Test
	public void testGetDistance() {
		Route route = new Route();

		Assert.assertEquals(0L, route.getDistance());

		route = new Route(1, 2);
		Assert.assertEquals(1L, route.getDistance());
	}

	/** test Set Distance method in Route Class. */
	@Test
	public void testSetDistance() {
		Route route = new Route();
		route.setDistance(5);

		Assert.assertEquals(5L, route.getDistance());

		route = new Route(1, 2);
		route.setDistance(-15);
		Assert.assertEquals(-15L, route.getDistance());
		route.setDistance(-5);
		Assert.assertEquals(-5L, route.getDistance());
	}

	/** test Get Duration method in Route Class. */
	@Test
	public void testGetDuration() {
		Route route = new Route();

		Assert.assertEquals(0L, route.getDuration());

		route = new Route(1, 2);
		Assert.assertEquals(2L, route.getDuration());
	}

	/** test Set Duration method in Route Class. */
	@Test
	public void testSetDuration() {
		Route route = new Route();
		route.setDuration(5);

		Assert.assertEquals(5L, route.getDuration());

		route = new Route(1, 2);
		route.setDuration(-15);
		Assert.assertEquals(-15L, route.getDuration());
		route.setDuration(-5);
		Assert.assertEquals(-5L, route.getDuration());
	}

	/** test that Routes made with an empty constructor are equal. */
	@Test
	public void testEqualsWithEmptyConstructor() {
		Route route = new Route();
		Route route0 = new Route(0, 0);

		Assert.assertEquals(route, route);

		Assert.assertNotEquals(null, route0);
		Assert.assertNotEquals(null, route);

	}

	/** test Set Duration method in Route Class. */
	@Test
	public void testEquals() {
		Route testRoute = new Route(2, 4);
		Route testRouteEq = new Route(2, 4);

		Route testNotEqual = new Route(6, 8);
		Route testNotReverse = new Route(4, 2);
		Route testFirstParamDiff = new Route(8, 4);
		Route testSecondParamDiff = new Route(2, 8);

		Assert.assertEquals(testRoute, testRoute);

		Assert.assertNotEquals(null, testRoute);

		Assert.assertEquals(testRouteEq, testRoute);

		Assert.assertNotEquals(testNotEqual, testRoute);
		Assert.assertNotEquals(testNotReverse, testRoute);
		Assert.assertNotEquals(testFirstParamDiff, testRoute);
		Assert.assertNotEquals(testSecondParamDiff, testRoute);
	}

	@Test
	public void hashCodeTest() {
		Route testRoute = new Route(2, 4);
		assertEquals(testRoute.hashCode(), 1027);
	}

	@Test
	public void toStringTest() {
		Route testRoute = new Route(0, 1);
		String toString = "Route [distance=0, duration=1]";
		assertTrue(testRoute.toString().equals(toString));
	}

	@Test
	public void equalsTest() {
		Route testRoute = new Route(0, 1);
		assertFalse(testRoute.equals(null));
	}

	@Test
	public void equalsTest2() {
		Route testRoute = new Route(0, 1);
		assertFalse(testRoute.equals(new User()));
	}
}
