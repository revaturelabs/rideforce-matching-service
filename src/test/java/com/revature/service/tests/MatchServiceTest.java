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
	
	{
		rider.setId(1);
		driver1.setId(2);
		driver2.setId(3);
		driver3.setId(4);
		driver4.setId(5);
		
		rider.setBatchEnd(new Date(2018,10,19));
		driver1.setBatchEnd(new Date(2018,10,19));
		driver2.setBatchEnd(new Date(2018,11,1));
		driver3.setBatchEnd(new Date(2018,10,12));
		driver4.setBatchEnd(new Date(2018,10,5));
		
		Like like1 = new Like(new Pair(1,2));
		Like like2 = new Like(new Pair(1,3));
		Dislike dislike1 = new Dislike(new Pair(1,4));
		likes.add(like1);
		likes.add(like2);
		dislikes.add(dislike1);
		
		likedIds.add(2);
		likedIds.add(3);
		dislikedIds.add(4);
	}
	
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
	public void testRankBatchEndDate_withSameEndDate_returns1() {
		Method rankByBatchEnd = null;
		double resultSameEndDate = 0.;
		try {
			rankByBatchEnd = MatchService.class.getDeclaredMethod("rankByBatchEnd",User.class, User.class);
			rankByBatchEnd.setAccessible(true);
			resultSameEndDate = (double) rankByBatchEnd.invoke(matchService, rider, driver1);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}		
		Assertions.assertThat(resultSameEndDate).isEqualTo(1.0);
	}
	
	@Test
	public void testRankBatchEndDate_withLaterEndDate_returns1() {
		Method rankByBatchEnd = null;
		double resultLaterEndDate = 0.;
		try {
			rankByBatchEnd = MatchService.class.getDeclaredMethod("rankByBatchEnd",User.class, User.class);
			rankByBatchEnd.setAccessible(true);
			resultLaterEndDate = (double) rankByBatchEnd.invoke(matchService, rider, driver2);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		Assertions.assertThat(resultLaterEndDate).isEqualTo(1.0);

	}
	
	@Test
	public void testRankBatchEndDate_withEarlierEndDate_returnsLessThan1() {
		Method rankByBatchEnd = null;
		double resultOneWeekEarlierEndDate = 0.;
		double resultTwoWeeksEarlierEndDate = 0.;
		try {
			rankByBatchEnd = MatchService.class.getDeclaredMethod("rankByBatchEnd",User.class, User.class);
			rankByBatchEnd.setAccessible(true);
			resultOneWeekEarlierEndDate = (double) rankByBatchEnd.invoke(matchService, rider, driver3);
			resultTwoWeeksEarlierEndDate = (double) rankByBatchEnd.invoke(matchService, rider, driver4);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		Assertions.assertThat(resultOneWeekEarlierEndDate).isLessThan(1.0);
		Assertions.assertThat(resultTwoWeeksEarlierEndDate).isLessThan(resultOneWeekEarlierEndDate);

	}
	
//	@Test
//	public void testRankByAffect_withNeutralAffect_returnsOneHalf() {
//		Method rankByAffect = null;
//		double result = 0.;
//		try {
//			rankByAffect = MatchService.class.getDeclaredMethod("rankByAffect", User.class, User.class, List.class, List.class);
//			rankByAffect.setAccessible(true);
//			result = (double) rankByAffect.invoke(matchService, rider, driver4, likedIds, dislikedIds);
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//		Assertions.assertThat(result).isEqualTo(0.5);
//	}
//	
//	@Test
//	public void testRankByAffect_withPositiveAffect_returnsOne() {
//		Method rankByAffect = null;
//		double result = 0.;
//		try {
//			rankByAffect = MatchService.class.getDeclaredMethod("rankByAffect", User.class, User.class, List.class, List.class);
//			rankByAffect.setAccessible(true);
//			result = (double) rankByAffect.invoke(matchService, rider, driver2, likedIds, dislikedIds);
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//		Assertions.assertThat(result).isEqualTo(1.0);
//	}
//	
//	@Test
//	public void testRankByAffect_withNegativeAffect_returnsZero() {
//		Method rankByAffect = null;
//		double result = 0.;
//		try {
//			rankByAffect = MatchService.class.getDeclaredMethod("rankByAffect", User.class, User.class, List.class, List.class);
//			rankByAffect.setAccessible(true);
//			result = (double) rankByAffect.invoke(matchService, rider, driver3, likedIds, dislikedIds);
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//		Assertions.assertThat(result).isEqualTo(0.0);
//	}
	
	// TODO: The below tests throw a null pointer exception when trying to call the tested method. Fixing required.
	/*
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLikedIds_returnsCorrectIds() {
		Method getLikedIds = null;
		List<Integer> resultList = null;
		try {
			getLikedIds = MatchService.class.getDeclaredMethod("getLikedIds", User.class);
			getLikedIds.setAccessible(true);
			Object result = getLikedIds.invoke(matchService, rider);
			if(result instanceof List<?> && ((List<?>)result).get(0) instanceof Integer) {	
				resultList = (List<Integer>) result;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		Assertions.assertThat(resultList).containsExactlyInAnyOrderElementsOf(likedIds);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDislikedIds_returnsCorrectIds() {
		Method getDislikedIds = null;
		List<Integer> resultList = null;
		try {
			getDislikedIds = MatchService.class.getDeclaredMethod("getDislikedIds", User.class);
			getDislikedIds.setAccessible(true);
			Object result = getDislikedIds.invoke(matchService, rider);
			if(result instanceof List<?> && ((List<?>)result).get(0) instanceof Integer) {	
				resultList = (List<Integer>) result;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		Assertions.assertThat(resultList).containsAnyElementsOf(dislikedIds);
	}
	*/
}
