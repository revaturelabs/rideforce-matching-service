package com.revature.repo.tests;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.LikeRepository;

/**
 * The Class LikeRepositoryRegressionTest. Repo regression tests are done only to check that
 * repo is empty and on custom repo methods.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class LikeRepositoryIntegrationTest {
	
	/** The entity manager. */
	@Autowired
	TestEntityManager testEntityManager;
	
	/** The like repository. */
	@Autowired
	private LikeRepository likeRepo;
	
	@Before
	public void setUp() {
		likeRepo.deleteAll();
		testEntityManager.persist(new Like(new Pair(1, 2)));
	}
	
	@Test
	public void validate() {
		assertNotNull(testEntityManager);
		assertNotNull(likeRepo);
	}

	/**
	 * Should be not be empty.
	 */
	@Test
	public void shouldNotBeEmpty() {
		List<Like> likes = likeRepo.findAll();
		assertThat(likes).hasSize(1);
	}
	
	/**
	 * Test find pair by user id.
	 */
	@Test
	public void testFindPairByUserId() {
		List<Like> likes = likeRepo.findByPairUserId(1);
		assertThat(likes).size().isEqualTo(1);
	}
	
	/**
	 * Test empty pair by user id. If given an invalid user id, returns empty value.
	 */
	@Test
	public void testEmptyPairWithInvalidUserId() {
		List<Like> likes = likeRepo.findByPairUserId(0);
		assertThat(likes).isEmpty();
	}

}
