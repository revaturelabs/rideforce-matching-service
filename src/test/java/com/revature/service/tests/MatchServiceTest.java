package com.revature.service.tests;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentMatchers.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
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
import com.revature.rideshare.matching.clients.MapsClient;
import com.revature.rideshare.matching.clients.UserClient;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.repositories.LikeRepository;
import com.revature.rideshare.matching.services.MatchService;
import com.revature.rideshare.matching.beans.Filter;

@SpringBootTest(classes= Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableAsync
public class MatchServiceTest {
	
	/**
	 * Ensures the configuration is such that the match service can be
	 * autowired for this test
	 * @author Tyler Bade
	 *
	 */
	@TestConfiguration
	static class MatchServiceTestContextConfiguration {
		@Bean
		public MatchService matchService() {
			return new MatchService();
		}
	}
	
	/**
	 * Mock beans are created here and the Match service is created.
	 * All dependent classes must be made into mock beans, otherwise the program will
	 * be unable to obtain the application context (at least from what I experienced)
	 */
	@InjectMocks
	@Autowired private MatchService matchService;
	@MockBean private LikeRepository likeRepository;
	@MockBean private DislikeRepository dislikeRepository;
	@MockBean private UserClient userClient;
	@MockBean private MapsClient mapsClient;
	@MockBean private RankByAffect rankByAffect;
	@MockBean private RankByBatchEnd rankByBatchEnd;
	@MockBean private RankByDistance rankByDistance;
	@MockBean private RankByStartTime rankByStartTime;
	
	/**
	 * These are variables that will be needed in this file
	 * Drivers will eventually hold the 4 drivers declared later
	 * The four filters will test all possible branches of the getFilteredMatches method
	 * The rider is a required variable for all methods in the match service
	 * The list of likes and dislikes is for the affect methods, and is also used in weights
	 */
	private static List<User> drivers = new ArrayList<User>();
	private static Filter fnone = new Filter();
	private static Filter fbend = new Filter(true,false,false);
	private static Filter fdstart = new Filter(false,true,false);
	private static Filter fdist = new Filter(false,false,true);
	private static User rider = new User();
	private static User driver1 = new User();
	private static User driver2 = new User();
	private static User driver3 = new User();
	private static User driver4 = new User();
	private static List<Integer> likedIds;
	private static List<Integer> dislikedIds;
	
	/**
	 * Initializes variables for use in testing
	 * test values are given to all users, drivers are added to the list
	 * The user and map clients are mocked here, as they are used in the Match service
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception {
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);
		driver4.setId(5);
		
		rider.setOffice("/offices/1");
		driver1.setOffice("/offices/1");
		driver2.setOffice("/offices/1");
		driver3.setOffice("/offices/2");
		driver4.setOffice("/offices/2");
		
		rider.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver1.setBatchEnd(new Date(Instant.parse("2018-10-19T00:00:00Z").toEpochMilli()));
		driver2.setBatchEnd(new Date(Instant.parse("2018-11-01T00:00:00Z").toEpochMilli()));
		driver3.setBatchEnd(new Date(Instant.parse("2018-10-12T00:00:00Z").toEpochMilli()));
		driver4.setBatchEnd(new Date(Instant.parse("2018-10-05T00:00:00Z").toEpochMilli()));
		
		drivers.add(driver1);
		drivers.add(driver2);
		drivers.add(driver3);
		drivers.add(driver4);
		
		Mockito.mock(UserClient.class);
		Mockito.mock(MapsClient.class);
	}
	
	/**
	 * This test checks all of the methods in the MatchService class
	 * It will run as though the rider is in the database, and returns an array of drivers
	 * That driver array is pushed through the remaining operations and is compared to the original list
	 * 
	 * @throws Exception
	 */
	@Test
	public void getMatchesInDatabase() throws Exception {
		/**
		 * This will force the userClient mock to act as if it found matches for the rider
		 * This declaration makes sure we do not actually need the user service to test this
		 */
		Mockito.when(userClient.findByOfficeAndRole(Mockito.anyInt(), Mockito.anyString())).thenReturn(drivers);
		
		/**
		 * Testing begins here, it goes through all of the methods in Match service
		 * with the mock above, it will test as though the rider is in the database
		 * These assertions test all branches of working calls to Match service
		 */
		Assert.assertEquals(drivers, matchService.findMatches(rider));
		Assert.assertEquals(drivers, matchService.findFilteredMatches(fnone, rider));
		Assert.assertEquals(drivers, matchService.findFilteredMatches(fbend, rider));
		Assert.assertEquals(drivers, matchService.findFilteredMatches(fdstart, rider));
		Assert.assertEquals(drivers, matchService.findFilteredMatches(fdist, rider));
		Assert.assertEquals(drivers, matchService.findMatchesByAffects(rider));
		Assert.assertEquals(drivers, matchService.findMatchesByBatchEnd(rider));
		Assert.assertEquals(drivers, matchService.findMatchesByStartTime(rider));
		Assert.assertEquals(drivers, matchService.findMatchesByDistance(rider));
	}
}
