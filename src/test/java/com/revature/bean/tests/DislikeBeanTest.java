
package com.revature.bean.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.HibernateValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;

/**
 * This test, DislikeBeanTest, will act as 
 * regression testing to ensure the stability of our 
 * dislike bean.
 */
public class DislikeBeanTest {
	
	/** The local validator factory. */
	private static LocalValidatorFactoryBean localValidatorFactory;

	/**
	 * Setup validator factory.
	 */
	@BeforeClass
	static public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	

	/**
	 * Test dislike constructor.
	 */
	@Test
	public void testDislikeConstructor() {

		Pair pair = new Pair(100, 200);
		Dislike dis = new Dislike(pair);
		

		assertTrue("Pair constructor accepting two ints did not create obj as expected.", dis.getPair().equals(pair));
	}
	
	/**
	 * Test dislike constructor when a null is passed.
	 */
	@Test
	public void testDislikeConstructorNull() {

		Dislike dis = new Dislike(null);
		Dislike disNoArg = new Dislike();

		Set<ConstraintViolation<Dislike>> violations = localValidatorFactory.validate(dis);
		Set<ConstraintViolation<Dislike>> violations1 = localValidatorFactory.validate(disNoArg);
		assertTrue(violations.size() == 1);
		assertTrue(violations1.size() == 1);
	}
	
	/**
	 * Test dislike constructor with an empty pair.
	 */
	@Test
	public void testDislikeConstructorEmptyPair() {

		Dislike dis = new Dislike(new Pair());

		Set<ConstraintViolation<Dislike>> violations = localValidatorFactory.validate(dis);
		assertTrue(violations.size() == 0);
	}
	
	@Test
	public void testDislikeToString() {

		Dislike dis = new Dislike(new Pair(1,2));

		assertTrue(dis.toString().equals("Dislike [userId=" + 1 + ", affectedId=" + 2 + "]"));
	}
	
	@Test
	public void testDislikeSetter() {

		Dislike dis = new Dislike(new Pair(1,2));
		Pair p = new Pair(3,4);
		dis.setPair(p);

		assertTrue(dis.getPair().equals(p));
	}
	
	@Test
	public void testHash() {

		Dislike dis = new Dislike(new Pair(1,2));
		Dislike dis1 = new Dislike(new Pair(1,2));

		assertTrue(dis.hashCode() == dis1.hashCode());
	}

	/**
	 * Test dislike equals method
	 */
	@Test
	public void testDislikeEquals() {

		Pair pair = new Pair(1, 2);
		Pair pairEq = new Pair(1, 2);
		Pair pairNotEq = new Pair(3, 4);
		Pair pairNotEq1 = new Pair(1, 4);

		Dislike dis = new Dislike(pair);
		Dislike disEq = new Dislike(pairEq);
		Dislike disNotEq = new Dislike(pairNotEq);
		Dislike disNotEq1 = new Dislike(pairNotEq1);

		assertTrue("Dislike equals override not functioning properly; should be true.", dis.equals(disEq));
		assertTrue("Dislike equals override not functioning properly; should be true.", dis.equals(dis));
		assertFalse("Dislike equals override not functioning properly; should be false.", dis.equals(pair));
		assertFalse("Dislike equals override not functioning properly; should be false.", dis.equals(disNotEq));
		assertFalse("Dislike equals override not functioning properly; should be false.", dis.equals(disNotEq1));
		assertFalse("Dislike equals override not functioning properly; should be false.", dis.equals(null));

	}

}
