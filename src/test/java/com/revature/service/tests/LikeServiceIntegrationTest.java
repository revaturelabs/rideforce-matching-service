package com.revature.service.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.LikeRepository;
import com.revature.rideshare.matching.services.LikeService;

/**
 * The Class LikeServiceIntegrationTest.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DirtiesContext(classMode= ClassMode.AFTER_CLASS)
@EnableAsync
public class LikeServiceIntegrationTest {
	
	/**
	 * The Class LikeServiceImplTestContextConfiguration.
	 */
	@TestConfiguration
	static class LikeServiceImplTestContextConfiguration {

		@Bean
		public LikeService likeService() {
			return new LikeService();
		}
	}

	/** The test entity manager. */
	@Autowired 
	TestEntityManager testEntityManager;
	
	/** The like repo. */
	@Autowired 
	LikeRepository likeRepo;
	
	/** The like service. */
	@Autowired
	LikeService likeService;	
	
	/** The thrown. */
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
	
	/**
	 * Validate.
	 */
	@Before
	public void validate() {
		assertNotNull(testEntityManager);
		
		testEntityManager.persist(new Like(new Pair(1, 2)));
		testEntityManager.persist(new Like(new Pair(2, 3)));
		testEntityManager.persist(new Like(new Pair(3, 4)));
	}
	
	/**
	 * Test get likes.
	 */
	@Test
	public void testGetLikes() {
		List<Like> likes = likeService.getLikes(1);
		assertThat(likes).hasSize(1);
	}
	
	/**
	 * Test save like.
	 */
	@Test
	public void testSaveLike() {
		Like like = new Like(new Pair(4, 5));
		likeService.saveLike(4, 5);
		
		List<Like> likes = likeService.getLikes(4);
		
		assertThat(likes.get(0)).isEqualTo(like);
	}
		
	/**
	 * Test delete like.
	 */
	@Test
	public void testDeleteLike() {
		likeService.deleteLike(4, 5);
		assertThat(likeService.getLikes(4)).hasSize(0);
			
	}
}
