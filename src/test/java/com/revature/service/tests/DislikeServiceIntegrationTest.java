package com.revature.service.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.matching.beans.Dislike;
import com.revature.rideshare.matching.beans.Pair;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.services.DislikeService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DislikeServiceIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DislikeRepository dislikeRepo;

	
	@Test
	public void shouldBeEmpty() {
		Iterable<Dislike> dis = dislikeRepo.findAll();
 
		assertThat(dis).isEmpty();
	}
 

}
