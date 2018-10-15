package com.revature.algorithm.tests;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.revature.rideshare.matching.algorithm.AggregateRankingBuilder;
import com.revature.rideshare.matching.algorithm.RankingCriterion;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.exceptions.DuplicateRankingCriteriaException;
import com.revature.rideshare.matching.exceptions.NoRankingCriteriaException;

public class AggregateRankingBuilderTest {

	private static final double CRITERION_A_RESULT = 0.25;
	private static final double CRITERION_B_RESULT = 0.33;
	private static final double CRITERION_C_RESULT = 0.1;
	private static final double CRITERION_A_WEIGHT = 1;
	private static final double CRITERION_B_WEIGHT = 2;
	private static final double CRITERION_C_WEIGHT = 3;

	/**
	 * Test class 1 for testing the AggregateRankingBuilder
	 */
	private class TestRankingCriteriaA extends RankingCriterion {

		@Override
		protected double rank(User rider, User driver) {
			return CRITERION_A_RESULT;
		}

	}
	
	/**
	 * Test class 2 for testing the AggregateRankingBuilder
	 */
	private class TestRankingCriteriaB extends RankingCriterion {

		@Override
		protected double rank(User rider, User driver) {
			return CRITERION_B_RESULT;
		}

	}

	/**
	 * Test class 3 for testing the AggregateRankingBuilder
	 */
	private class TestRankingCriteriaC extends RankingCriterion {

		@Override
		protected double rank(User rider, User driver) {
			return CRITERION_C_RESULT;
		}

	}

	@Test
	public void rankMatch_whenNoCriteriaAdded_throwsNoRankingCriteriaException() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		try {
			arb.rankMatch(rider, driver);
		} catch (NoRankingCriteriaException nrce) {
			return;
		}
		Assertions.failBecauseExceptionWasNotThrown(NoRankingCriteriaException.class);
		;
	}

	@Test
	public void rankMatch_whenPassedNullParameters_throwsIllegalArgumentException() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		try {
			arb.rankMatch(null, null);
		} catch (IllegalArgumentException iae) {
			return;
		}
		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		;
	}

	@Test
	public void rankMatch_whenPassedNullRider_throwsIllegalArgumentException() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User driver = new User();
		try {
			arb.rankMatch(null, driver);
		} catch (IllegalArgumentException iae) {
			return;
		}
		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		;
	}

	@Test
	public void rankMatch_whenPassedNullDriver_throwsIllegalArgumentException() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		try {
			arb.rankMatch(rider, null);
		} catch (IllegalArgumentException iae) {
			return;
		}
		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		;
	}

	@Test
	public void rankMatch_whenOneCriterionAdded_returnsExpectedValue() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		RankingCriterion testCriterionA = new TestRankingCriteriaA();
		arb.addCriterion(testCriterionA);
		double result = arb.rankMatch(rider, driver);
		Assertions.assertThat(result).isEqualTo(CRITERION_A_RESULT);
	}

	@Test
	public void rankMatch_whenTwoUniqueUnweightedCriteriaAdded_returnsExpectedValue() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		RankingCriterion testCriterionA = new TestRankingCriteriaA();
		RankingCriterion testCriterionB = new TestRankingCriteriaB();
		arb.addCriterion(testCriterionA);
		arb.addCriterion(testCriterionB);
		double result = arb.rankMatch(rider, driver);

		double expectedResult = (CRITERION_A_RESULT + CRITERION_B_RESULT) / 2;
		Assertions.assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void rankMatch_whenTwoUniqueWeightedCriteriaAdded_returnsExpectedValue() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		RankingCriterion testCriterionA = new TestRankingCriteriaA();
		RankingCriterion testCriterionB = new TestRankingCriteriaB();
		testCriterionA.setWeight(CRITERION_A_WEIGHT);
		testCriterionB.setWeight(CRITERION_B_WEIGHT);
		arb.addCriterion(testCriterionA);
		arb.addCriterion(testCriterionB);
		double result = arb.rankMatch(rider, driver);

		double expectedResult = (CRITERION_A_WEIGHT * CRITERION_A_RESULT + CRITERION_B_WEIGHT * CRITERION_B_RESULT)
				/ (CRITERION_A_WEIGHT + CRITERION_B_WEIGHT);
		Assertions.assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void rankMatch_whenTwoDuplicateCriteriaAdded_throwsDuplicateRankingCriteriaException() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		RankingCriterion testCriterionA_1 = new TestRankingCriteriaA();
		RankingCriterion testCriterionA_2 = new TestRankingCriteriaA();		
		arb.addCriterion(testCriterionA_1);
		try {
			arb.addCriterion(testCriterionA_2);
		} catch (DuplicateRankingCriteriaException drce) {
			return;
		}
		
		Assertions.failBecauseExceptionWasNotThrown(DuplicateRankingCriteriaException.class);
	}

	@Test
	public void rankMatch_whenThreeUniqueUnweightedCriteriaAdded_returnsExpectedValue() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		RankingCriterion testCriterionA = new TestRankingCriteriaA();
		RankingCriterion testCriterionB = new TestRankingCriteriaB();
		RankingCriterion testCriterionC = new TestRankingCriteriaC();
		arb.addCriterion(testCriterionA);
		arb.addCriterion(testCriterionB);
		arb.addCriterion(testCriterionC);
		double result = arb.rankMatch(rider, driver);

		double expectedResult = (CRITERION_A_RESULT + CRITERION_B_RESULT + CRITERION_C_RESULT) / 3;
		Assertions.assertThat(result).isEqualTo(expectedResult);
	}
	
	@Test
	public void rankMatch_whenThreeUniqueWeightedCriteriaAdded_returnsExpectedValue() {
		AggregateRankingBuilder arb = new AggregateRankingBuilder();
		User rider = new User();
		User driver = new User();
		RankingCriterion testCriterionA = new TestRankingCriteriaA();
		RankingCriterion testCriterionB = new TestRankingCriteriaB();
		RankingCriterion testCriterionC = new TestRankingCriteriaC();
		testCriterionA.setWeight(CRITERION_A_WEIGHT);
		testCriterionB.setWeight(CRITERION_B_WEIGHT);
		testCriterionC.setWeight(CRITERION_C_WEIGHT);
		arb.addCriterion(testCriterionA);
		arb.addCriterion(testCriterionB);
		arb.addCriterion(testCriterionC);
		double result = arb.rankMatch(rider, driver);

		double expectedResult = (
				CRITERION_A_WEIGHT * CRITERION_A_RESULT + 
				CRITERION_B_WEIGHT * CRITERION_B_RESULT + 
				CRITERION_C_WEIGHT * CRITERION_C_RESULT)
				/ (CRITERION_A_WEIGHT + CRITERION_B_WEIGHT + CRITERION_C_WEIGHT);
		Assertions.assertThat(result).isEqualTo(expectedResult);
	}

}
