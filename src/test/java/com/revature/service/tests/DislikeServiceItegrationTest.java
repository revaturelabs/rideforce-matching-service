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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.services.DislikeService;

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@DirtiesContext(classMode= ClassMode.AFTER_CLASS)
public class DislikeServiceItegrationTest {
	
	@TestConfiguration
    static class DislikeServiceImplTestContextConfiguration {
  
        @Bean
        public DislikeService dislikeService() {
            return new DislikeService();
        }
    }
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DislikeService dislikeServ;
	
	@Autowired
	private DislikeRepository dislikeRepo;

	@Before
	public void setUp() {
	 
	   entityManager.persist(new Dislike(new Pair(1,2)));
	   entityManager.persist(new Dislike(new Pair(10,20)));
	   entityManager.persist(new Dislike(new Pair(11,21)));
	   entityManager.persist(new Dislike(new Pair(10,21)));
	   entityManager.persist(new Dislike(new Pair(10,2)));
	}
	
	
	@Test
	public void validate() {
		assertNotNull(entityManager);
		assertNotNull(dislikeRepo);
		assertThat(dislikeRepo.findAll()).size().isEqualTo(5);
		assertNotNull(dislikeServ);
	}
	
	@Test
	public void saveDislikeTest() {
		
		Dislike dis = new Dislike(new Pair(100, 200));
		dislikeServ.saveDislike(100, 200);
		List<Dislike> dees = dislikeServ.getDislikes(100);
		
		assertThat(dees.get(0)).isEqualTo(dis);
	}
	
	@Test
	public void getDislikesTest() {
		List<Dislike> dees = dislikeServ.getDislikes(10);
		assertThat(dees).size().isEqualTo(3);
	}

	@Test
	public void deleteDislikeTest() {
		dislikeServ.deleteDislike(10,21);
		assertThat(dislikeServ.getDislikes(10)).size().isEqualTo(2);
	
		
	}
	
}
