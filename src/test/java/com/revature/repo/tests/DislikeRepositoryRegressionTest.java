package com.revature.repo.tests;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;

/**
 * This Class DislikeRepositoryRegressionTest will provide testing of the repository to ensure that
 * the repository will be initialized properly, and that our custom repository methods are functioning
 * normally.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class DislikeRepositoryRegressionTest {

	/** The entity manager. */
	@Autowired
	private TestEntityManager entityManager;
	
	/** The dislike repo. */
	@Autowired
	private DislikeRepository dislikeRepo;

	
	/**
	 * Should be empty.
	 */
	@Test
	public void shouldBeEmpty() {
		List<Dislike> dis = dislikeRepo.findAll();
		Assertions.assertThat(dis).isEmpty();
	}
	
	/**
	 * Test find by pair user id.
	 */
	@Test
	public void testFindByPairUserId() {
		
		entityManager.persist(new Dislike(new Pair(1,2)));
		entityManager.persist(new Dislike(new Pair(1,4)));
		entityManager.persist(new Dislike(new Pair(10,2)));
		entityManager.persist(new Dislike(new Pair(11,2)));
		entityManager.persist(new Dislike(new Pair(2,2)));
		List<Dislike> dees = dislikeRepo.findByPairUserId(1);
		
		Assertions.assertThat(dees).size().isEqualTo(2);
		
	}
 

}
