package com.revature.service.tests;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	/*
	@Test
	public void testFindTheGreatestFromAllData() {
		
		DataService dataServiceMock = mock(DataService.class);
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 24, 15, 3 });
		SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceMock);
		int result = businessImpl.findTheGreatestFromAllData();
		assertEquals(24, result);
		
	}
	*/

}
