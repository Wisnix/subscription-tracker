package com.subscriptiontracker.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.service.SubscriptionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubscriptionService subscriptionService;

	private static ObjectMapper mapper;

	@BeforeAll
	static void setUp() {
		mapper = new ObjectMapper();
	}
	
	@Test
	public void findSubscriptionsByUserIdTestBasic() throws Exception {
		List<Subscription> subscriptionList=Arrays.asList(
				new Subscription(1, 1, LocalDate.of(2022, 3, 19), "weekly", "Netflix subscription", "active", 14,20.0f),
				new Subscription(2, 1, LocalDate.of(2022, 3, 19), "weekly", "Spotify subscription", "active", 14,20.0f),
				new Subscription(3, 1, LocalDate.of(2022, 3, 19), "weekly", "Jakas tam paroweczki", "active", 14,20.0f));
		String subscriptionListJSON=mapper.writeValueAsString(subscriptionList);
		
		when(subscriptionService.findByUserId(1)).thenReturn(subscriptionList);

		RequestBuilder request = MockMvcRequestBuilders.get("/users/1/subscriptions")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json(subscriptionListJSON))
				.andReturn();
	}

	@Test
	public void findSubscriptionsByUserIdTestEmptyResponse() throws Exception {
		when(subscriptionService.findByUserId(1)).thenReturn(new ArrayList<Subscription>());

		RequestBuilder request = MockMvcRequestBuilders.get("/users/1/subscriptions")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json("[]"))
				.andReturn();
	}

	@Test
	public void findSubscriptionsByUserIdTestBadResponse() throws Exception {
		when(subscriptionService.findByUserId(1)).thenReturn(new ArrayList<Subscription>());

		RequestBuilder request = MockMvcRequestBuilders.get("/users/john/subscriptions")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}

}
