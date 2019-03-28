package com.revature.algorithm.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.algorithm.RankByAffect;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.repositories.LikeRepository;
import com.revature.rideshare.matching.services.DislikeService;
import com.revature.rideshare.matching.services.LikeService;

/**
 * The Class RankByAffectTest.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@EnableAsync
public class RankByAffectTest {

	/**
	 * The Class TestContextConfiguration.
	 */
	@TestConfiguration
	static class TestContextConfiguration {

		/**
		 * LikeService mock bean.
		 *
		 * @return the like service
		 */
		@Bean
		public LikeService likeService() {
			return new LikeService();
		}

		/**
		 * DislikeService mock bean.
		 *
		 * @return the dislike service
		 */
		@Bean
		public DislikeService dislikeService() {
			return new DislikeService();
		}

		/**
		 * RankByAffect mock bean.
		 *
		 * @return the rank by affect
		 */
		@Bean
		public RankByAffect rankByAffect() {
			return new RankByAffect();
		}
	}

	/** The test entity manager. */

	@Autowired
	TestEntityManager testEntityManager;

	/** The like repository. */
	@Autowired
	LikeRepository likeRepo;

	/** The like service. */
	@Autowired
	LikeService likeService;

	/** The dislike repository. */
	@Autowired
	DislikeRepository dislikeRepo;

	/** The dislike service. */
	@Autowired
	DislikeService dislikeService;

	/** The rank by affect. */
	@Autowired
	RankByAffect rankByAffect;

	/** The rider. */
	static User rider = new User();

	/** The driver 1. */
	static User driver1 = new User();

	/** The driver 2. */
	static User driver2 = new User();

	/** The driver 3. */
	static User driver3 = new User();

	/**
	 * Sets up the mock data and cleans the application context.
	 */
	@Before
	public void setUp() {

		likeRepo.deleteAll();
		testEntityManager.persist(new Like(new Pair(1, 2)));
		testEntityManager.persist(new Like(new Pair(3, 4)));
		testEntityManager.persist(new Like(new Pair(5, 6)));

		dislikeRepo.deleteAll();
		testEntityManager.persist(new Dislike(new Pair(1, 6)));
		testEntityManager.persist(new Dislike(new Pair(3, 2)));
		testEntityManager.persist(new Dislike(new Pair(5, 4)));

		rider.setId(1);
		driver1.setId(2);
		driver2.setId(6);
		driver3.setId(4);
	}

	/**
	 * Validates setup variables.
	 */
	@Test
	public void validate() {
		assertNotNull(testEntityManager);
		assertNotNull(likeRepo);
		assertNotNull(likeService);
		assertNotNull(dislikeRepo);
		assertNotNull(dislikeService);
		assertNotNull(rankByAffect);
	}

	/**
	 * Rank with null rider should throw illegal argument exception.
	 */
	@Test
	public void rank_withNullRider_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, driver1);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	/**
	 * Tests that rank with null driver throws illegal argument exception.
	 */
	@Test
	public void rank_withNullDriver_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(rider, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	/**
	 * Tests that rank with null parameters throws illegal argument exception.
	 */
	@Test
	public void rank_withNullParameters_shouldThrowIllegalArgumentException() {
		try {
			invokeRank(null, null);
		} catch (IllegalArgumentException iae) {
			return;
		}

		Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
	}

	/**
	 * Tests that initializeAffects populates likedIds and dislikedIds lists
	 */
	@Test
	public void testRankByAffect_initializeAffects() {
		Method initializeAffects = null;
		try {
			initializeAffects = RankByAffect.class.getDeclaredMethod("initializeAffects", User.class);
			initializeAffects.setAccessible(true);
			initializeAffects.invoke(rankByAffect, rider);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		List<Integer> likedIdsList = rankByAffect.getLikedIds();
		List<Integer> dislikedIdsList = rankByAffect.getDislikedIds();
		assertThat(likedIdsList).hasSize(1);
		assertThat(dislikedIdsList).hasSize(1);
	}

	/**
	 * Tests that liked affect returns a 1 for rider-driver ranking.
	 */
	@Test
	public void testRankByAffect_liked_returns1() {
		double resultLikedAffect = invokeRank(rider, driver1);
		assertThat(resultLikedAffect).isEqualTo(1.0);
	}

	/**
	 * Tests that disliked affect returns a 0 for rider-driver ranking.
	 */
	@Test
	public void testRankByAffect_disliked_returns0() {
		double resultdisLikedAffect = invokeRank(rider, driver2);
		assertThat(resultdisLikedAffect).isEqualTo(0.0);
	}

	/**
	 * Tests that neutral (neither liked nor disliked) affect returns a 0.5 for
	 * rider-driver ranking.
	 */
	@Test
	public void testRankByAffect_neutral_returnsLessThan1() {
		double resultNeutralAffect = invokeRank(rider, driver3);
		assertThat(resultNeutralAffect).isEqualTo(0.5);
	}

	/**
	 * Invoke rank. Method uses reflection to access private rank method from
	 * RankByAffect.
	 *
	 * @param rider  the rider
	 * @param driver the driver
	 * @return the double value representing rider-driver ranking
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public double invokeRank(User rider, User driver) throws IllegalArgumentException {
		Method rank = null;
		double result = -1;
		try {
			rank = RankByAffect.class.getDeclaredMethod("rank", User.class, User.class);
			rank.setAccessible(true);
			result = (double) rank.invoke(rankByAffect, rider, driver);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException();
		}

		return result;
	}

}
