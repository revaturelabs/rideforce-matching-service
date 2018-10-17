package com.revature.service.tests;

import java.lang.reflect.Field;
import java.time.Instant;
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

import com.revature.algorithm.tests.TestMapsClient;
import com.revature.algorithm.tests.TestUserClient;
import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.algorithm.RankByAffect;
import com.revature.rideshare.matching.algorithm.RankByBatchEnd;
import com.revature.rideshare.matching.algorithm.RankByDistance;
import com.revature.rideshare.matching.algorithm.RankByStartTime;
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
	
	private static MatchService matchService = new MatchService();
	private static TestUserClient testUserClient = new TestUserClient();
	private static TestMapsClient testMapsClient = new TestMapsClient();

	
	private static User rider;
	private static User driver1;
	private static User driver2;
	private static User driver3;
	private static User driver4;
	private static List<Integer> likedIds;
	private static List<Integer> dislikedIds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rider = new User();
		driver1 = new User();
		driver2 = new User();
		driver3 = new User();
		driver4 = new User();
		
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);
		driver4.setId(5);
		
		rider.setOffice("/offices/1");
		
		rider.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver1.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver2.setBatchEnd(new Date(Instant.parse("2018-11-01T00:00:00Z").toEpochMilli()));
		driver3.setBatchEnd(new Date(Instant.parse("2018-10-12T00:00:00Z").toEpochMilli()));
		driver4.setBatchEnd(new Date(Instant.parse("2018-10-05T00:00:00Z").toEpochMilli()));

		Field rankByAffectField = MatchService.class.getDeclaredField("rankByAffect");
		Field rankByBatchEndField = MatchService.class.getDeclaredField("rankByBatchEnd");
		Field rankByDistanceField = MatchService.class.getDeclaredField("rankByDistance");
		Field rankByStartTimeField = MatchService.class.getDeclaredField("rankByStartTime");
		Field mapsClientField = RankByDistance.class.getDeclaredField("testMapsClient");
		Field userClientField = MatchService.class.getDeclaredField("matchService");
		
		mapsClientField.set(rankByDistanceField, testMapsClient);
		userClientField.set(matchService, testUserClient);
		
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
