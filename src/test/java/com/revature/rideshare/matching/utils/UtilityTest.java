package com.revature.rideshare.matching.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilityTest {

	@Test
	public void testDistanceDoubleDoubleDoubleDoubleString() {
		double actual = Utility.distance(0, 0, 50, 70, "m");
		assertTrue(5337 - 10 < actual && actual < 5337 + 10);
	}

}
