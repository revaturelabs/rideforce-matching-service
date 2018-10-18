package com.revature.bean.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.andrewoma.dexx.collection.TreeSet;
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
	 * Tests valid user.
	 */

	@Test
	public void testUserValid() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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

	/**
	 * Tests user id setter and getter.
	 */
	@Test
	public void testUserId() {
		User user = new User();
		user.setId(1);
		assertEquals(1, user.getId());
	}

	/**
	 * Tests that user first name being empty is a constraint violation.
	 */
	@Test
	public void testUserFirstNameEmpty() {
		user = new User(100, "", "lastName", "email@email.com", "password", "pic-url.com", "true", "role", "office",
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
	 * Tests that user first name being null is a constraint violation.
	 */
	@Test
	public void testUserFirstNameNull() {
		user = new User(100, null, "lastName", "email@email.com", "password", "pic-url.com", "true", "role", "office",
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
	 * Tests that user last name being empty is a constraint violation.
	 */
	@Test
	public void testUserLastNameEmpty() {
		user = new User(100, "firstName", "", "email@email.com", "password", "pic-url.com", "true", "role", "office",
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
	 * Test's that user last name being null is a constraint violation.
	 */
	@Test
	public void testUserLastNameNull() {
		user = new User(100, "firstName", null, "email@email.com", "password", "pic-url.com", "true", "role", "office",
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
	 * Tests that user email being empty is a constraint violation.
	 */
	@Test
	public void testUserEmailEmpty() {
		user = new User(100, "firstName", "lastName", "", "password", "pic-url.com", "true", "role", "office", "home",
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
	 * Tests that user email being null is a constraint violation.
	 */
	@Test
	public void testUserEmailNull() {
		user = new User(100, "firstName", "lastName", null, "password", "pic-url.com", "true", "role", "office", "home",
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
	 * Tests that user role being empty is a constraint violation.
	 */
	@Test
	public void testUserRoleEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "",
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
	 * Tests that user role being null is a constraint violation.
	 */
	@Test
	public void testUserRoleNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", null,
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
	 * Tests that user office being empty is a constraint.
	 */
	@Test
	public void testUserOfficeEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role", "",
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
	 * Tests that user office being null is a constraint violation.
	 */
	@Test
	public void testUserOfficeNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				null, "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

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
	 * Tests that user address being empty is a constraint violation.
	 */
	@Test
	public void testUserAddressEmpty() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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
	 * Tests that user address being null is a constraint violation.
	 */
	@Test
	public void testUserAddressNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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
	 * Tests that user date being null is a constraint violation.
	 */
	@Test
	public void testUserDateNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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
	 * Tests that user cars set being null is a constraint violation.
	 */
	@Test
	public void testUserCarsSetNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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
	 * Tests that user contact info set being null is a constraint violation.
	 */
	@Test
	public void testUserContactInfoSetNull() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
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

	/**
	 * Tests user constructor with required-only fields.
	 */
	@Test
	public void testUserRequiredOnlyConstructor() {
		user = new User(100, "firstName", "lastName", "email@email.com", "role", "office", "home", new Date(2018),
				new HashSet<String>(), new HashSet<String>(), 8.5f);

		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(user);
		int counter = 0;

		for (ConstraintViolation<User> v : violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(0);
	}

	/**
	 * Tests user first name getter and setter.
	 */
	@Test
	public void testUserFirstNameGetterSetter() {
		user = new User();
		user.setFirstName("FirstName");

		assertSame("FirstName", user.getFirstName());
	}

	/**
	 * Tests user last name getter and setter.
	 */
	@Test
	public void testUserLastNameGetterSetter() {
		user = new User();
		user.setLastName("LastName");

		assertSame("LastName", user.getLastName());
	}

	/**
	 * Tests user email getter and setter.
	 */
	@Test
	public void testUserEmailGetterSetter() {
		user = new User();
		user.setEmail("email");

		assertSame("email", user.getEmail());
	}

	/**
	 * Tests user password getter and setter.
	 */
	@Test
	public void testUserPasswordGetterSetter() {
		user = new User();
		user.setPassword("password");

		assertSame("password", user.getPassword());
	}

	/**
	 * Tests user photo url getter and setter.
	 */
	@Test
	public void testUserPhotoUrlGetterSetter() {
		user = new User();
		user.setPhotoUrl("photo.url");

		assertSame("photo.url", user.getPhotoUrl());
	}

	/**
	 * Tests user active getter and setter.
	 */
	@Test
	public void testUserActiveGetterSetter() {
		user = new User();
		user.setActive("active");

		assertSame("active", user.isActive());
	}

	/**
	 * Tests user role getter and setter.
	 */
	@Test
	public void testUserRoleGetterSetter() {
		user = new User();
		user.setRole("role");

		assertSame("role", user.getRole());
	}

	/**
	 * Tests user office getter and setter.
	 */
	@Test
	public void testUserOfficeGetterSetter() {
		user = new User();
		user.setOffice("office");

		assertSame("office", user.getOffice());
	}

	/**
	 * Tests user address getter and setter.
	 */
	@Test
	public void testUserAddressGetterSetter() {
		user = new User();
		user.setAddress("home");

		assertSame("home", user.getAddress());
	}

	/**
	 * Tests user cars getter and setter.
	 */
	@Test
	public void testUserCarsGetterSetter() {
		user = new User();
		Set<String> cars = new HashSet<>();
		cars.add("car1");
		cars.add("cars2");
		user.setCars(cars);

		assertTrue(user.getCars().size() == 2);
	}

	/**
	 * Tests user venmo getter and setter.
	 */
	@Test
	public void testUserVenmoGetterSetter() {
		user = new User();
		user.setVenmo("venmo");

		assertSame("venmo", user.getVenmo());
	}

	/**
	 * Tests user contact info getter and setter.
	 */
	@Test
	public void testUserContactInfoGetterSetter() {
		user = new User();
		Set<String> contact = new HashSet<>();
		contact.add("phone");
		contact.add("email");
		contact.add("slack");
		user.setContactInfo(contact);

		assertTrue(user.getContactInfo().size() == 3);
	}

	/**
	 * Tests user toString.
	 */
	@Test
	public void testUserToString() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);

		assertTrue(user.toString().equals(
				"User [id=100, firstName=firstName, lastName=lastName, email=email@email.com, password=password, photoUrl=pic-url.com, active=true, role=role, office=office, address=home, batchEnd=Wed Dec 31 19:00:02 EST 1969, cars=[], venmo=venmo, contactInfo=[], startTime=8.5]"));
	}

	/**
	 * Tests user hash code.
	 */
	@Test
	public void testUserHashCode() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user1 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		
		assertTrue(user.hashCode() == user1.hashCode());
	}
	
	/**
	 * Tests user equals.
	 */
	@Test
	public void testUserEquals() {
		user = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user1 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user2 = new User(200, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user3 = new User(100, "abc", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user4 = new User(100, "firstName", "abc", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user5 = new User(100, "firstName", "lastName", "abc", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user6 = new User(100, "firstName", "lastName", "email@email.com", "abc", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user7 = new User(100, "firstName", "lastName", "email@email.com", "password", "abc", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user8 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "abc", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user9 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "abc",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user10 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"abc", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user11 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "abc", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user12 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", null, new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user13 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), null, "venmo", new HashSet<String>(), 8.5f);
		User user14 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "abc", new HashSet<String>(), 8.5f);
		User user15 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", null, 8.5f);
		User user16 = new User(100, null, "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user17 = new User(100, "firstName", null, "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user18 = new User(100, "firstName", "lastName", null, "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user19 = new User(100, "firstName", "lastName", "email@email.com", null, "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user20 = new User(100, "firstName", "lastName", "email@email.com", "password", null, "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user21 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", null, "role",
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user22 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", null,
				"office", "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user23 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				null, "home", new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user24 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", null, new Date(2018), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user25 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2017), new HashSet<String>(), "venmo", new HashSet<String>(), 8.5f);
		User user26 = new User(100, "firstName", "lastName", "email@email.com", "password", "pic-url.com", "true", "role",
				"office", "home", new Date(2018), new HashSet<String>(), null, new HashSet<String>(), 8.5f);

		

		
		assertTrue("User equals override not functioning properly; should be true", user.equals(user));
		assertTrue("User equals override not functioning properly; should be true", user.equals(user1));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user2));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user3));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user4));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user5));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user6));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user7));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user8));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user9));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user10));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user11));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user12));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user13));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user14));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user15));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user16));
		assertFalse("User equals override not functioning properly; should be false", user.equals(null));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user17));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user18));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user19));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user20));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user21));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user22));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user23));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user24));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user25));
		assertFalse("User equals override not functioning properly; should be false", user.equals(user26));
	}

}
