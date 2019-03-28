package com.revature.rideshare.matching.comparators;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.CachedLocation;
import com.revature.rideshare.matching.beans.User;

public class ProximityComparatorTest {
	
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
	public void testDistanceDoubleDoubleDoubleDoubleString() {
		User rider = new User();
		rider.setLocation(new CachedLocation("", 0, 0));
		ProximityComparator pc = new ProximityComparator(rider);
		assertEquals(-1, pc.compare(driver1, driver2));
		assertEquals(1, pc.compare(driver2, driver1));
		driver1.setLocation(driver2.getLocation());
		assertEquals(0, pc.compare(driver1, driver2));
	}

}
