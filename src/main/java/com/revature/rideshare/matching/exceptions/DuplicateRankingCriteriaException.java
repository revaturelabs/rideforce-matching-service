package com.revature.rideshare.matching.exceptions;

public class DuplicateRankingCriteriaException extends RuntimeException {

	/**
	 * Thrown when trying to apply two ranking criterion of the same type to the
	 * AggregateRankingBuilder
	 */
	private static final long serialVersionUID = -5052350963381122150L;

	public DuplicateRankingCriteriaException(String message) {
		super(message);
	}

}
