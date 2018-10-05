package com.revature.bean.tests;

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

		Assert.assertEquals(message, re.getMessage());
	}
	
	/** Tests string constructor sets the message correctly, and that the get 
	 * function gets that message correctly. */
	@Test
	public void testGetMessageConstructedString() {
		String message = "ASDF";
		
		ResponseError re = new ResponseError(message);
		
		Assert.assertEquals(message, re.getMessage());
	}
	
	/** Tests setMessage sets the message correctly, and that the get 
	 * function gets that message correctly. */
	@Test
	public void testSetAndGetMessage() {
		String message = "ASDF";
		
		ResponseError re = new ResponseError(new Exception());
		re.setMessage(message);
		
		Assert.assertEquals(message, re.getMessage());
	}
	
	
	/** Tests setDetails sets the details correctly, and that the get 
	 * function gets that details correctly. */
	@Test
	public void testSetAndGetDetails() {
		String[] details = {"ASDF", "QWER", "ZXCV"};
		
		ResponseError re = new ResponseError(new Exception());
		re.setDetails(details);
		
		// Use this test if the expected arrays are the same object
		Assert.assertSame(details, re.getDetails());
		
		// Use this test if the returned array is a different array with the same values
		Assert.assertArrayEquals(details, re.getDetails());
	}
	
	
	/** Tests withDetails sets the details correctly, and that the get 
	 * function gets that details correctly. */
	@Test
	public void testWithDetails() {
		String[] details = {"ASDF", "QWER", "ZXCV"};
		
		ResponseError re = new ResponseError(new Exception());
		
		// Test that it does return the same object for chaining
		Assert.assertSame(re, re.withDetails(details));
		
		// Use this test if the expected arrays are the same object
		Assert.assertSame(details, re.getDetails());
		
		// Use this test if the returned array is a different array with the same values
		Assert.assertArrayEquals(details, re.getDetails());
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
		
		Assert.assertEquals(respErr, respEnt.getBody());
		Assert.assertEquals(status, respEnt.getStatusCode());
	}
	
	
}
