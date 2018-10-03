package com.revature.service.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.services.DislikeService;

@RunWith(SpringRunner.class)
public class DislikeServiceIntegrationTest {

	@TestConfiguration
	static class DislikeServiceTestContextConfiguration {

		@Bean
		public DislikeService disService() {
			return new DislikeService();
		}
	}

	@Autowired
	private DislikeService disService;

	@MockBean
	private DislikeRepository dislikeRepo;

	@Before
	public void setUp() {
	    Dislike dis0 = new Dislike(new Pair(1,2));
	    Dislike dis1 = new Dislike(new Pair(1,3));
	    Dislike dis2 = new Dislike(new Pair(1,4));
	    List<Dislike> dees = new ArrayList<>();
	 
	    Mockito.when(dislikeRepo.findByPairUserId(1)).thenReturn(dees);
	}

}
