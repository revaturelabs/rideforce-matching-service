package com.revature.rideshare.matching.filters;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.User;

public class StatusFilterTest {

	private static User driver1;
	private static User driver2;
	private static User rider;
	
	@BeforeClass
	public static void settup( ) {
		driver1 = new User();
		driver1.setActive("Active");

		driver2 = new User();
		driver2.setActive("Active");
		rider = new User();
		rider.setActive("InActive");		
	}
	
	@Test
	public void testFilter() {
		StatusFilter sf = new StatusFilter("Active");
		assertTrue(sf.filter(driver1));
		assertFalse(sf.filter(rider));
		assertTrue(sf.filter(driver2));
	}

}
