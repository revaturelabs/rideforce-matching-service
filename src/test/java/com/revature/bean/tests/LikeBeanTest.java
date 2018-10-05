package com.revature.bean.tests;
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
	
	/** The like. */
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
}
