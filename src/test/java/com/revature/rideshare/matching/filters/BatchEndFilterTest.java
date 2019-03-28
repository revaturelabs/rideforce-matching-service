package com.revature.rideshare.matching.filters;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.User;

public class BatchEndFilterTest {

	private static User driver1;
	private static User driver2;
	private static Calendar cal = Calendar.getInstance();
	
	@BeforeClass
	public static void settup( ) {
		cal.set(2019, 2, 12);
		driver1 = new User();
		driver1.setBatchEnd(cal.getTime());
		cal.clear();
		cal.set(2019, 2, 13);
		driver2 = new User();
		driver2.setBatchEnd(cal.getTime());
	}
	
	@Test
	public void testFilter() {
		User rider = new User();
		cal.clear();
		cal.set(2019, 2, 9);
		rider.setBatchEnd(cal.getTime());
		BatchEndFilter bef = new BatchEndFilter(rider, 1);
		assertTrue(bef.filter(driver1));
		assertTrue(bef.filter(driver2));
		cal.clear();
		cal.set(2019, 2, 5);
		rider.setBatchEnd(cal.getTime());
		bef = new BatchEndFilter(rider, 1);
		assertFalse(bef.filter(driver1));
		assertFalse(bef.filter(driver2));
		cal.clear();
		cal.set(2019, 2, 6);
		rider.setBatchEnd(cal.getTime());
		bef = new BatchEndFilter(rider, 1);
		assertTrue(bef.filter(driver1));
		assertFalse(bef.filter(driver2));
		cal.clear();
		cal.set(2019, 2, 19);
		rider.setBatchEnd(cal.getTime());
		bef = new BatchEndFilter(rider, 1);
		assertFalse(bef.filter(driver1));
		assertTrue(bef.filter(driver2));
	}

}
