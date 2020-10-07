package com.subscriptiontracker.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

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
	void testCreateSubscriptionBasic() throws Exception {
		Subscription subscription = new Subscription(1, 1, LocalDate.of(2022, 3, 19), "weekly", "Netflix subscription",
				"active", 14,10.0f,true);
		String subscriptionJSON = mapper.writeValueAsString(subscription);

		when(subscriptionService.save(subscription)).thenReturn(subscription);

		RequestBuilder request = MockMvcRequestBuilders.post("/subscriptions")
				.accept(MediaType.APPLICATION_JSON)
				.content(subscriptionJSON).
				contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(content().json(subscriptionJSON))
				.andReturn();
	}

	@Test
	void testCreateSubscriptionNullFields() throws Exception {
		Subscription subscription = new Subscription(null, 1, null, null, null, null, null,null,true);
		Subscription returnedSubscription = new Subscription(1, 1, null, null, null, null, null,null,true);
		when(subscriptionService.save(subscription)).thenReturn(returnedSubscription);

		String subscriptionJSON = mapper.writeValueAsString(subscription);
		String returnedSubscriptionJSON = mapper.writeValueAsString(returnedSubscription);

		RequestBuilder request = MockMvcRequestBuilders.post("/subscriptions")
				.accept(MediaType.APPLICATION_JSON)
				.content(subscriptionJSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(content().json(returnedSubscriptionJSON))
				.andReturn();
	}
	
	@Test
	void testDeleteSubscriptionBasic()throws Exception {
		int subscriptionId=1;

		when(subscriptionService.findById(subscriptionId)).thenReturn(new Subscription());

		RequestBuilder request = MockMvcRequestBuilders.delete("/subscriptions/"+subscriptionId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isNoContent())
				.andExpect(content().string("Deleted subscritpion: " + subscriptionId))
				.andReturn();
	}
	
	@Test
	void testDeleteSubscriptionBasicWrongId()throws Exception {
		int subscriptionId=1;

		when(subscriptionService.findById(subscriptionId)).thenReturn(null);

		RequestBuilder request = MockMvcRequestBuilders.delete("/subscriptions/"+subscriptionId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	void testUpdateSubscriptionbasic() throws Exception {
		int subscriptionId=1;
		Subscription subscription = new Subscription(1, 1, LocalDate.of(2022, 3, 19), "weekly", "Netflix subscription",
				"active", 14,9.99f,true);
		Subscription newSubscription = new Subscription(1, 1, LocalDate.of(2022, 3, 20), "monthly", "Spotify subscription",
				"active", 30,199.99f,true);
		String subscriptionJSON = mapper.writeValueAsString(newSubscription);

		when(subscriptionService.findById(subscriptionId)).thenReturn(subscription);
		when(subscriptionService.save(newSubscription)).thenReturn(newSubscription);

		RequestBuilder request = MockMvcRequestBuilders.put("/subscriptions")
				.accept(MediaType.APPLICATION_JSON)
				.content(subscriptionJSON).
				contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json(subscriptionJSON))
				.andReturn();
	}
	
	@Test
	void testUpdateSubscriptionWrongId() throws Exception {
		int subscriptionId=2;
		Subscription subscription = new Subscription(1, 1, LocalDate.of(2022, 3, 19), "weekly", "Netflix subscription",
				"active", 14,25.99f,true);
		Subscription newSubscription = new Subscription(1, 1, LocalDate.of(2022, 3, 20), "weekly", "Spotify subscription",
				"active", 30,30f,true);
		String subscriptionJSON = mapper.writeValueAsString(newSubscription);

		when(subscriptionService.findById(2)).thenReturn(null);

		RequestBuilder request = MockMvcRequestBuilders.put("/subscriptions")
				.accept(MediaType.APPLICATION_JSON)
				.content(subscriptionJSON).
				contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();
	}

}
