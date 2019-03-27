package com.revature.rideshare.matching.exceptions;

public class ImproperRankingReturnedException extends RuntimeException {

	/**
	 * Thrown when ranks returned from RankingCriterion are not between 0 and 1
	 */
	private static final long serialVersionUID = -7432281320676904626L;

	public ImproperRankingReturnedException(String message) {
		super(message);
	}

}
