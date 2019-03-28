package com.revature.rideshare.matching.comparators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rideshare.matching.beans.User;

public class BatchEndComparatorTest {

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
	public void testCompare() {
		User rider = new User();
		cal.clear();
		cal.set(2019, 2, 9);
		rider.setBatchEnd(cal.getTime());
		BatchEndComparator bec = new BatchEndComparator(rider);
		assertEquals(-1, bec.compare(driver1, driver2));
		assertEquals(1, bec.compare(driver2, driver1));
		driver2.setBatchEnd(driver1.getBatchEnd());
		assertEquals(0, bec.compare(driver2, driver1));
	}

}
