package com.revature.controller.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.config.TestConfig;
import com.revature.rideshare.matching.Application;


@RunWith(SpringRunner.class)
//@WebMvcTest(Application.class)
@SpringBootTest(classes = Application.class)
//@ContextHierarchy({
//    @ContextConfiguration(classes = TestConfig.class)})
@AutoConfigureMockMvc
public class MatchingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	// Current build allows anyone to get request all users, this should be changed to be more secure
	@Test
	public void loggedOutUserCanGetUsers() throws Exception {
		this.mockMvc.perform(get("/matches/test")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("/matches/1")).andExpect(status().isOk());
	}

}