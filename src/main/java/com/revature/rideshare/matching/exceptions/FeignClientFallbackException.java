package com.revature.rideshare.matching.exceptions;

/**
 * This class represents the exception that will be thrown when Hystrix triggers
 * the fallback implementation of a FeignClient and no reasonable data can be
 * returned from the fallback.
 */
public class FeignClientFallbackException extends RuntimeException {

	/**
	 * Generated serial id for this class.
	 */
	private static final long serialVersionUID = 2302161426282924479L;

	/**
	 * Constructs a new FeignClientFallbackException.
	 */
	public FeignClientFallbackException() {
		super("Couldn't make connection with FeignClient");
	}

	/**
	 * Constructs a new FeignClientFallbackException with the specified message.
	 * 
	 * @param message - The string message related to this exception.
	 */
	public FeignClientFallbackException(String message) {
		super(message);
	}
}
