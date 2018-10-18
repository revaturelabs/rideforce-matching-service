package com.revature.bean.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class LikeTest. 
 */
public class LikeBeanTest {
	
	/** The local validator factory. */
	private static LocalValidatorFactoryBean localValidatorFactory;
	
	/**  Test data objects. */
	private Like like;
	
	/** The pair. */
	private Pair pair;
	
	/**
	 * Setup validator factory.
	 */
	@BeforeClass
	public static void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	/**
	 * Test Like constructor.
	 */
	@Test
	public void testLikeConstructor() {
		pair = new Pair(100, 200);
		like = new Like(pair);
		
		assertTrue("should be same", like.getPair().equals(pair));	
	}
	
	/**
	 * Test Like with empty pair.
	 */
	@Test
	public void testLikeWithEmptyPair() {
		like = new Like(new Pair());
		
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<Like>> violations = validator.validate(like);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	/**
	 * Test Like with null pair.
	 */
	@Test
	public void testLikeWithNullPair() {
		like = new Like(null);
		
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<Like>> violations = validator.validate(like);
		Assertions.assertThat(violations.size()).isEqualTo(1);		
	}
	
	/**
	 * Test Like equals.
	 */
	@Test
	public void testLikeEquals() {
		Pair pair = new Pair(100, 200);
		Pair pairEq = new Pair(100, 200);
		Pair pairNotEq1 = new Pair(200, 300);
		Pair pairNotEq2 = new Pair(100, 300);
		
		Like like = new Like(pair);
		Like likeEq = new Like(pairEq);
		Like likeNotEq1 = new Like(pairNotEq1);
		Like likeNotEq2 = new Like(pairNotEq2);
		
		assertTrue("Like equals override not functioning properly; should be true", like.equals(likeEq));
		assertTrue("Like equals override not functioning properly; should be true", like.equals(like));
		assertFalse("Like equals override not functioning properly; should be false", like.equals(likeNotEq1));
		assertFalse("Like equals override not functioning properly; should be false", like.equals(likeNotEq2));
		assertFalse("Like equals override not functioning properly; should be false", like.equals(pair));
		assertFalse("Like equals override not functioning properly; should be false", like.equals(null));
	}
	
	/**
	 * Test Like setPair.
	 */
	@Test
	public void testLikeSetPair() {
		Like like = new Like();
		Pair pair = new Pair(100, 200);
		like.setPair(pair);
		
		assertSame(like.getPair(), pair);
	}
	
	/**
	 * Test Like toString.
	 */
	@Test
	public void testLikeToString() {
		Like like = new Like(new Pair(100, 200));
		
		assertTrue(like.toString().equals("Like [userId=" + 100 + ", affectedId=" + 200 + "]"));
	}
	
	/**
	 * Test Like hash code.
	 */
	@Test
	public void testLikeHashCode() {
		Like like1 = new Like(new Pair(100, 200));
		Like like2 = new Like(new Pair(100, 200));
		Pair pair = null;
		Like like3 = new Like(pair);
		assertTrue(like1.hashCode() == like2.hashCode());
		assertFalse(like3.hashCode() == like2.hashCode());
	}
}
