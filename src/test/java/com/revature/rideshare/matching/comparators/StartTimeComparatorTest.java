package com.revature.rideshare.matching.comparators;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.User;

public class StartTimeComparatorTest {

	private static User driver1;
	private static User driver2;
	private static Calendar cal = Calendar.getInstance();
	
	@BeforeClass
	public static void settup( ) {
		driver1 = new User();
		driver1.setStartTime(9);
		driver2 = new User();
		driver2.setStartTime(10);
	}
	
	@Test
	public void testCompare() {
		User rider = new User();
		rider.setStartTime(9.5f);
		StartTimeComparator stc = new StartTimeComparator(rider);
		assertEquals(0, stc.compare(driver1, driver2));
		rider.setStartTime(9.16f);
		stc = new StartTimeComparator(rider);
		assertEquals(-1, stc.compare(driver1, driver2));
		rider.setStartTime(9.80f);
		stc = new StartTimeComparator(rider);
		assertEquals(1, stc.compare(driver1, driver2));
	}

}
