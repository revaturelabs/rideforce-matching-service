package com.revature.rideshare.matching.beans;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The error type that is returned in conjunction with an HTTP error status.
 * 
 * This class is meant to be very easy to use in conjunction with controller
 * methods. For example, the code snippet below shows how one might use a
 * ResponseError in a controller method returning a
 * {@link org.springframework.http.ResponseEntity ResponseEntity}:
 * 
 * <pre>
 * {@code
 * return new ResponseError("Something went wrong.")
 *     .withDetails("Here's the first problem.", "And here's the second.")
 *     .toResponseEntity(HttpStatus.BAD_REQUEST);
 * }
 * </pre>
 */
public class ResponseError {
	/**
	 * The primary message describing the error.
	 */
	@NotEmpty
	private String message;
	/**
	 * Any additional details that may be relevant to the error (can be empty).
	 */
	@NotNull
	private String[] details = {};

	/**
	 * Constructs a new {@code ResponseError} from the given exception.
	 * 
	 * @param e the exception from which to construct the {@code ResponseError}. The
	 *          exception's message will be used as the message for the
	 *          {@code ResponseError}, but the stack trace will not be included in
	 *          the details (for security reasons).
	 */
	public ResponseError(Exception e) {
		this(e.getMessage());
	}

	/**
	 * Constructs a new {@code ResponseError} with the given message.
	 * 
	 * @param message the message to describe the error
	 */
	public ResponseError(String message) {
		this.message = message;
	}

	/**
	 * Sets the details of this {@code ResponseError}, returning the caller object.
	 * This is a helpful wrapper for {@link #setDetails(String) setDetails} that
	 * allows for more factory-like usage.
	 * 
	 * @param details the details to associate with this error. These will overwrite
	 *                any existing details that were specified earlier.
	 * @return the caller object, to allow for method chaining
	 */
	public ResponseError withDetails(String... details) {
		this.setDetails(details);
		return this;
	}

	/**
	 * Wraps this {@code ResponseError} in a
	 * {@link org.springframework.http.ResponseEntity ResponseEntity} to allow it to
	 * be returned from a controller method.
	 * 
	 * @param status the status code to send with the error
	 * @return the wrapped {@code ResponseError}
	 */
	public ResponseEntity<ResponseError> toResponseEntity(HttpStatus status) {
		return new ResponseEntity<ResponseError>(this, status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getDetails() {
		return details;
	}

	public void setDetails(String[] details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(details);
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseError other = (ResponseError) obj;
		if (!Arrays.equals(details, other.details))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseError [message=" + message + ", details=" + Arrays.toString(details) + "]";
	}
}
