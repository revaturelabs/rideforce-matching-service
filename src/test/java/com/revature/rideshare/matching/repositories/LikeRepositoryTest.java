package com.revature.rideshare.matching.repositories;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.beans.Like;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikeRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LikeRepository likeRepository;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		this.entityManager.persist(new Like(1,1));
		List<Like> like = this.likeRepository.findByUserId(1);		
		assertNotNull(like);
	}

}
