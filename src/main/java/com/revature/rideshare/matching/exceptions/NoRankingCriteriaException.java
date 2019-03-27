package com.revature.rideshare.matching.exceptions;

public class NoRankingCriteriaException extends RuntimeException {

	/**
	 * Thrown when no ranking criterion have been applied to the
	 * AggregateRankingBuilder
	 */
	private static final long serialVersionUID = 951744734171433934L;

	public NoRankingCriteriaException(String message) {
		super(message);
	}

}
