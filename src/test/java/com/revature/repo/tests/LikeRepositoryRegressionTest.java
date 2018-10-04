package com.revature.repo.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class LikeRepositoryRegressionTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	private LikeRepository likeRepo;

	@Test
	public void shouldBeEmpty() {
		List<Like> likes = likeRepo.findAll();
		assertThat(likes).hasSize(0);
	}
	
	@Test
	public void testFindPairByUserId() {
		entityManager.persist(new Like(new Pair(1, 2)));
		List<Like> likes = likeRepo.findByPairUserId(1);
		
		assertThat(likes).size().isEqualTo(1);
	}

}
