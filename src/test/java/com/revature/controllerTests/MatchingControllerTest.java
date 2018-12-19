package com.revature.controllerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.rideshare.matching.Application;
import com.revature.rideshare.matching.beans.User;
import com.revature.rideshare.matching.repositories.DislikeRepository;
import com.revature.rideshare.matching.repositories.LikeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MatchingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private DislikeRepository dislikeRepository;
	
	@Before
	public void setUp() {
		User u = new User();
		u.setId(0);
	}
	
	@Test
	public void getAllMatchesTest() throws Exception {
		this.mockMvc.perform(get("/matches/0")).andExpect(status().isOk());
	}
	
	@Test 
	public void getAllMatchesTest1() throws Exception {
		this.mockMvc.perform(get("/matches/1")).andExpect(status().isOk());
	}
	
	@Test
	public void getAllFilteredNullUser() throws Exception {
		this.mockMvc.perform(post("/matches/filtered")).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void getAllMinusAffectsValidUserTest() throws Exception {
		this.mockMvc.perform(get("/matches/likes-dislikes/0")).andExpect(status().isOk());
	}
	
	@Test
	public void getByDistanceValidUser() throws Exception {
		this.mockMvc.perform(get("/matches/distance/0")).andExpect(status().isOk());
	}
	
	@Test
	public void getByBatchEndValidUser() throws Exception {
		this.mockMvc.perform(get("/matches/batch-end/0")).andExpect(status().isOk());
	}
	
	@Test
	public void getByStartTimeValidUser() throws Exception {
		this.mockMvc.perform(get("/matches/start-time/0")).andExpect(status().isOk());
	}
	
	@Test
	public void getLikedValidUser() throws Exception {
		this.mockMvc.perform(get("/matches/likes/0")).andExpect(status().isOk());
	}
	
	@Test
	public void getDislikedValidUser() throws Exception {
		this.mockMvc.perform(get("/matches/dislikes/0")).andExpect(status().isOk());
	}
}
