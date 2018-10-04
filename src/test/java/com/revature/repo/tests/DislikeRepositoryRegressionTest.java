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

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class DislikeRepositoryRegressionTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DislikeRepository dislikeRepo;

	
	@Test
	public void shouldBeEmpty() {
		List<Dislike> dis = dislikeRepo.findAll();
		Assertions.assertThat(dis).isEmpty();
	}
	
	@Test
	public void testFindByPairUserId() {
		
		entityManager.persist(new Dislike(new Pair(1,2)));
		List<Dislike> dees = dislikeRepo.findByPairUserId(1);
		
		Assertions.assertThat(dees).size().isEqualTo(1);
		
	}
 

}
