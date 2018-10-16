package com.revature.bean.tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideshare.matching.beans.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserBeanTest.
 */
public class UserBeanTest {

	/** The local validator factory. */
	private static LocalValidatorFactoryBean localValidatorFactory;

	/** The user. */
	private User user;

	/**
	 * Setup validator factory.
	 */
	@BeforeClass
	public static void setupValidatorFactory() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	/**
	 * Test user valid.
	 */

	@Test
	public void testUserValid() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(0);
	}

	@Test
	public void testUserId() {
		User user = new User();
		user.setId(1);
		assertEquals(1, user.getId());
	}

	/**
	 * Test user first name empty.
	 */
	@Test
	public void testUserFirstNameEmpty() {
		user = new User(100, "", "lastName", "email@email.com", "password", "pic-url.com", true, "role", "office",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user first name null.
	 */
	@Test
	public void testUserFirstNameNull() {
		user = new User(100, null, "lastName", "email@email.com", "password", "pic-url.com", true, "role", "office",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user last name empty.
	 */
	@Test
	public void testUserLastNameEmpty() {
		user = new User(100, "firstName", "", "email@email.com", "password", "pic-url.com", true, "role", "office",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user last name null.
	 */
	@Test
	public void testUserLastNameNull() {
		user = new User(100, "firstName", null, "email@email.com", "password", "pic-url.com", true, "role", "office",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user email empty.
	 */
	@Test
	public void testUserEmailEmpty() {
		user = new User(100, "firstName", "lastName", "", "password", "pic-url.com", true, "role", "office", "home",
				new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user email null.
	 */
	@Test
	public void testUserEmailNull() {
		user = new User(100, "firstName", "lastName", null, "password", "pic-url.com", true, "role", "office", "home",
				new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user role empty.
	 */
	@Test
	public void testUserRoleEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "", "office",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user role null.
	 */
	@Test
	public void testUserRoleNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, null,
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user office empty.
	 */
	@Test
	public void testUserOfficeEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role", "",
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user office null.
	 */
	@Test
	public void testUserOfficeNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role", null,
				"home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user address empty.
	 */
	@Test
	public void testUserAddressEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", "", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user address null.
	 */
	@Test
	public void testUserAddressNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", null, new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user date null.
	 */
	@Test
	public void testUserDateNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", "home", null, new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user cars set null.
	 */
	@Test
	public void testUserCarsSetNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", "home", new Date(2018), null, "venmo", new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	/**
	 * Test user contact info set null.
	 */
	@Test
	public void testUserContactInfoSetNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", true, "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", null, 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
}
