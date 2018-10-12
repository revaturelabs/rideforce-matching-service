package com.revature.service.tests;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.services.DislikeService;

/**
 * This Class, DislikeServiceItegrationTest, is used to test the dislikeService to ensure
 * code stability throughout code development.
 */
@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@DirtiesContext(classMode= ClassMode.AFTER_CLASS)
@EnableAsync
public class DislikeServiceItegrationTest {
	
	/**
	 * The Class DislikeServiceImplTestContextConfiguration.
	 * This class is just for setting up mock service for testing
	 */
	@TestConfiguration
    static class DislikeServiceImplTestContextConfiguration {
  
        /**
         * returns a mock of the Dislike service.
         *
         * @return the dislike service
         */
        @Bean
        public DislikeService dislikeService() {
            return new DislikeService();
        }
    }
	
	/** The entity manager. */
	@Autowired
	private TestEntityManager entityManager;
	
	/** The dislike serv. */
	@Autowired
	private DislikeService dislikeServ;
	
	/** The dislike repo. */
	@Autowired
	private DislikeRepository dislikeRepo;

	/**
	 * Sets up the mock data for testing.
	 */
	@Before
	public void setUp() {
	 
	   entityManager.persist(new Dislike(new Pair(1,2)));
	   entityManager.persist(new Dislike(new Pair(10,20)));
	   entityManager.persist(new Dislike(new Pair(11,21)));
	   entityManager.persist(new Dislike(new Pair(10,21)));
	   entityManager.persist(new Dislike(new Pair(10,2)));
	}
	
	
	/**
	 * Validates that the setup for testing is established correctly.
	 */
	@Test
	public void validate() {
		assertNotNull(entityManager);
		assertNotNull(dislikeRepo);
		assertThat(dislikeRepo.findAll()).size().isEqualTo(5);
		assertNotNull(dislikeServ);
	}
	
	/**
	 * Tests the save method of the dislike service.
	 */
	@Test
	public void saveDislikeTest() {
		
		Dislike dis = new Dislike(new Pair(100, 200));
		dislikeServ.saveDislike(100, 200);
		List<Dislike> dees = dislikeServ.getDislikes(100);
		
		assertThat(dees.get(0)).isEqualTo(dis);
	}
	
	/**
	 * Tests the get method of the dislike service. getDislikes should return 
	 * all dislikes in the database with a user id matching the id passed.
	 */
	@Test
	public void getDislikesTest() {
		List<Dislike> dees = dislikeServ.getDislikes(10);
		assertThat(dees).size().isEqualTo(3);
	}

	/**
	 * Test the delete function of the Dislike Service. The dislike with the matching id's passed
	 * (userId and AffectedId) should be deleted from the database.
	 */
	@Test
	public void deleteDislikeTest() {
		dislikeServ.deleteDislike(10,21);
		assertThat(dislikeServ.getDislikes(10)).size().isEqualTo(2);
	
		
	}
	
}
