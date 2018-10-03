package com.revature.bean.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;

public class DislikeBeanTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	

	@Test
	public void testDislikeConstructor() {

		Dislike dis = new Dislike(new Pair(100, 200));

		assertSame("Pair constructor accepting two ints did not create obj as expected.", 100,
				dis.getPair().getUserId());
		assertSame("Pair constructor accepting two ints did not create obj as expected.", 200,
				dis.getPair().getAffectedId());
	}
	
	@Test
	public void testDislikeConstructorEmptyPair() {

		Dislike dis = new Dislike(new Pair());

		assertSame("Pair constructor accepting two ints did not create obj as expected.", 100,
				dis.getPair().getUserId());
		assertSame("Pair constructor accepting two ints did not create obj as expected.", 200,
				dis.getPair().getAffectedId());
	}

	public void testDislikeEquals() {

		Pair pair = new Pair(1, 2);
		Pair pairEq = new Pair(1, 2);
		Pair pairNotEq = new Pair(3, 4);

		Dislike dis = new Dislike(pair);
		Dislike disEq = new Dislike(pairEq);
		Dislike disNotEq = new Dislike(pairNotEq);

		assertTrue("Dislike equals override not functioning properly; should be true.", dis.equals(disEq));
		assertFalse("Dislike equals override not functioning properly; should be false.", dis.equals(disNotEq));

	}

}
