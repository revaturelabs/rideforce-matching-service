package com.revature.bean.tests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.ResponseError;

public class ResponseErrorBeanTest {
	
	/** The local validator factory. */
	private static LocalValidatorFactoryBean localValidatorFactory;
	
	/**
	 * Setup validator factory.
	 */
	@BeforeClass
	public static void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	/** Tests that the constructors don't throw exceptions. */
	@Test
	public void testIntantiation() {
		new ResponseError(new Exception());
		new ResponseError("");
		
		
	}
	
	/** Tests exception constructor sets the message correctly, and that the get 
	 * function gets that message correctly. */
	@Test
	public void testGetMessageConstructedException() {
		String message = "ASDF";
		
		ResponseError re = new ResponseError(new Exception(message));

		// Assert that the message was received
		Assert.assertEquals(message, re.getMessage());
		
		// Assert that there are no component violations
		assertViolations(0, re);
	}
	
	/** Tests string constructor sets the message correctly, and that the get 
	 * function gets that message correctly. */
	@Test
	public void testGetMessageConstructedString() {
		String message = "ASDF";
		
		ResponseError re = new ResponseError(message);
		
		// Assert that the message was received
		Assert.assertEquals(message, re.getMessage());
		
		// Assert that there are no component violations
		assertViolations(0, re);
	}
	
	/** Tests setMessage sets the message correctly, and that the get 
	 * function gets that message correctly. */
	@Test
	public void testSetAndGetMessage() {
		String message = "ASDF";
		
		ResponseError re = new ResponseError(new Exception());
		re.setMessage(message);
		
		Assert.assertEquals(message, re.getMessage());
		
		assertViolations(0, re);
	}
	
	
	/** Tests setDetails sets the details correctly, and that the get 
	 * function gets that details correctly. */
	@Test
	public void testSetAndGetDetails() {
		String[] details = {"ASDF", "QWER", "ZXCV"};
		
		ResponseError re = new ResponseError(new Exception("1234"));
		re.setDetails(details);
		
		// Use this test if the expected arrays are the same object
		Assert.assertSame(details, re.getDetails());
		
		// Use this test if the returned array is a different array with the same values
		Assert.assertArrayEquals(details, re.getDetails());
		
		assertViolations(0, re);
	}
	
	
	/** Tests withDetails sets the details correctly, and that the get 
	 * function gets that details correctly. */
	@Test
	public void testWithDetails() {
		String[] details = {"ASDF", "QWER", "ZXCV"};
		
		ResponseError re = new ResponseError(new Exception("1234"));
		
		// Test that it does return the same object for chaining
		Assert.assertSame(re, re.withDetails(details));
		
		// Use this test if the expected arrays are the same object
		Assert.assertSame(details, re.getDetails());
		
		// Use this test if the returned array is a different array with the same values
		Assert.assertArrayEquals(details, re.getDetails());
		
		assertViolations(0, re);
	}
	
	
	/** Tests that the toResponseEntity method returns the appropriate 
	 * object. */
	@Test
	public void testToResponseEntity() {
		String message = "ASDF";
		String[] details = {"QWER", "ZXCV", "JKL:"};
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		ResponseError respErr = new ResponseError(message).withDetails(details);
		ResponseEntity<ResponseError> respEnt = respErr.toResponseEntity(status);
		
		// Assert that the object is equal to the original passed in objects.
		Assert.assertEquals(respErr, respEnt.getBody());
		Assert.assertEquals(status, respEnt.getStatusCode());
		
		assertViolations(0, respEnt.getBody());
	}
	
	/** Tests that an empty message from a constructor throws a violation with 
	 * an empty exception. */
	@Test
	public void testExceptionConstructorViolatesWithEmptyMessage() {
		ResponseError re = new ResponseError(new Exception());
		
		assertViolations(1, re);
	}
	
	/** Tests that an empty message from a constructor throws a violation with 
	 * an exception with a null message. */
	@Test
	public void testExceptionConstructorViolatesWithNullMessage() {
		ResponseError re = new ResponseError(new Exception((String) null));
		
		assertViolations(1, re);
	}
	
	
	/** Tests that an empty message from a constructor throws a violation with 
	 * an null string message. */
	@Test
	public void testMessageConstructorViolatesWithNullMessage() {
		ResponseError re = new ResponseError((String) null);
		
		assertViolations(1, re);
	}
	
	
	/** Tests that an empty message from a constructor throws a violation with 
	 * an empty string message. */
	@Test
	public void testMessageConstructorViolatesWithEmptyMessage() {
		ResponseError re = new ResponseError("");
		
		assertViolations(1, re);
	}
	
	
	
	/** Tests that there is a violation if the details are null. */
	@Test
	public void testNullDetailsIsViolation() {
		ResponseError re = new ResponseError("ASDF");
		re.setDetails(null);
		
		assertViolations(1, re);
	}
	
	
	/** Tests that there are 2 violations if the details are null and message
	 * are null. */
	@Test
	public void testNullMessageAndDetailsAreViolations() {
		ResponseError re = new ResponseError((String) null);
		re.setDetails(null);
		
		assertViolations(2, re);
	}
	
	
	// ============================================================================
	// Helper Methods
	// ============================================================================
	
	/** Accepts an object of Type T, and will fail if the number of violations, as 
	 * computed by the {@code LocalValidatorFactoryBean}, doesn't equal the 
	 * expected number of violations. 
	 * @param expectedViolations - The expected number of constraint violations from 
	 * 							the provided bean. 
	 * @param beanToTest - The bean to test for violations. 
	 */
	public <T> void assertViolations(int expectedViolations, T beanToTest) {
		Set<ConstraintViolation<T>> violations;
		violations = localValidatorFactory.validate(beanToTest);
		Assert.assertEquals(expectedViolations, violations.size());
	}
	
}









