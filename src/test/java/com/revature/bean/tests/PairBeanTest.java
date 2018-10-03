package com.revature.bean.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Pair;

public class PairBeanTest {
	
private LocalValidatorFactoryBean localValidatorFactory;

	

	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	@Test
	public void testConstructor() {
		
		Pair pair = new Pair(1, 2);
		
		
		assertSame("Pair constructor accepting two ints did not create obj as expected.", 1, pair.getUserId());
		assertSame("Pair constructor accepting two ints did not create obj as expected.", 2, pair.getAffectedId());
	}

	@Test
	public void testPairEquals() {
		
		Pair pair = new Pair(1, 2);
		Pair pairEq = new Pair(1, 2);
		Pair pairNotEq = new Pair(3, 4);
		
		assertTrue("Pair equals override not functioning properly; should be true.", pair.equals(pairEq));
		assertFalse("Pair equals override not functioning properly; should be false.", pair.equals(pairNotEq));
		
	}
	
	@Test
	public void testGetUserId() {
		Pair p = new Pair(101,201);
		assertTrue(p.getUserId() == 101);
	}
	
	@Test
	public void testGetAffectedId() {
		Pair p = new Pair(101,201);
		assertTrue(p.getAffectedId() == 201);
	}
	
	@Test
	public void testZeroIdOnPair() {
		Pair p = new Pair(0, 0);
        Set<ConstraintViolation<Pair>> violations = localValidatorFactory.validate(p);
        assertTrue(violations.size() == 2);
	}
	
	@Test
	public void testNegativeIdOnPair() {
		Pair p = new Pair(-1, -1);
        Set<ConstraintViolation<Pair>> violations = localValidatorFactory.validate(p);
        assertTrue(violations.size() == 2);
	}
	
	@Test
	public void test1AsIdOnPair() {
		Pair p = new Pair(1, 1);
        Set<ConstraintViolation<Pair>> violations = localValidatorFactory.validate(p);
        assertTrue(violations.size() == 0);
	}
}
