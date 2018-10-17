package com.revature.bean.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.HibernateValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Pair;

public class PairBeanTest {

	private static LocalValidatorFactoryBean localValidatorFactory;

	@BeforeClass
	static public void setupValidatorFactory() {
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
		Pair pairNotEq1 = new Pair(1, 4);

		assertTrue("Pair equals override not functioning properly; should be true.", pair.equals(pairEq));
		assertFalse("Pair equals override not functioning properly; should be false.", pair.equals(pairNotEq));
		assertFalse("Pair equals override not functioning properly; should be false.", pair.equals(null));
		assertFalse("Pair equals override not functioning properly; should be false.", pair.equals(pairNotEq1));

	}

	@Test
	public void testGetUserId() {
		Pair p = new Pair(101, 201);
		assertTrue(p.getUserId() == 101);
	}

	@Test
	public void testGetAffectedId() {
		Pair p = new Pair(101, 201);
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
	
	@Test
	public void testUserIdSetter() {
		Pair p = new Pair(1, 1);
		p.setUserId(100);;
		
		assertTrue("Setter did not work: affectedId did not change as expected", p.getUserId() == 100);
		
	}
	@Test
	public void testAffectedIdSetter() {
		Pair p = new Pair(1, 1);
		p.setAffectedId(200);
		
		assertTrue("Setter did not work: affectedId did not change as expected", p.getAffectedId() == 200);
		
	}
	
	@Test
	public void testPairToString() {

		Pair p = new Pair(1,2);

		assertTrue(p.toString().equals("userId=" + 1 + ", affectedId=" + 2));
	}
	
	@Test
	public void testHash() {
		Pair p = new Pair(1, 1);
		Pair p1 = new Pair(1, 1);
		
		assertTrue(p.hashCode() == p1.hashCode());
		
	}
}
