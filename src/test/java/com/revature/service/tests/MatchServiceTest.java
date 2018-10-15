package com.revature.service.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Like;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.repositories.LikeRepository;
import com.revature.rideshare.matching.services.MatchService;

@SpringBootTest(classes= Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableAsync
public class MatchServiceTest {
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private DislikeRepository dislikeRepository;
	
	private MatchService matchService = new MatchService();
	
	
	User rider = new User();
	User driver1 = new User();
	User driver2 = new User();
	User driver3 = new User();
	User driver4 = new User();
	List<Like> likes = new ArrayList<>();
	List<Dislike> dislikes = new ArrayList<>();
	List<Integer> likedIds = new ArrayList<>();
	List<Integer> dislikedIds = new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void validate() {
		Assertions.assertThat(likeRepository).isNotNull();
		Assertions.assertThat(dislikeRepository).isNotNull();
		
		Like like = new Like(new Pair(1,2));
		testEntityManager.persist(like);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		
	}
	
}
