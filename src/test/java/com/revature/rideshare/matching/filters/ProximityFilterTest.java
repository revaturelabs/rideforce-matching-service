package com.revature.rideshare.matching.filters;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.CachedLocation;
import com.revature.rideshare.matching.beans.User;

public class ProximityFilterTest {

	private static User driver1;
	private static User driver2;
	
	@BeforeClass
	public static void settup( ) {
		driver1 = new User();
		driver1.setLocation(new CachedLocation("", 10, 20));
		driver2 = new User();
		driver2.setLocation(new CachedLocation("", 40, 30));
	}
	
	@Test
	public void testFilter() {
		User rider = new User();
		rider.setLocation(new CachedLocation("", 0, 0));
		ProximityFilter pf = new ProximityFilter(rider, 50);
		assertFalse(pf.filter(driver1));
		assertFalse(pf.filter(driver2));
		driver1.setLocation(new CachedLocation("", 0.5, 0.5));
		assertTrue(pf.filter(driver1));
		driver1.setLocation(new CachedLocation("", 0.6, 0.5));
		assertFalse(pf.filter(driver1));
	}

}
