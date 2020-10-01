package com.subscriptiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.error.SubscriptionNotFoundException;
import com.subscriptiontracker.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping
	public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
		Subscription savedSubscription=subscriptionService.save(subscription);
		return new ResponseEntity<>(savedSubscription,HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubscription(@PathVariable int id) throws Exception {
		Subscription subscription = subscriptionService.findById(id);
		if (subscription == null)
			throw new SubscriptionNotFoundException("Subscription id not found - " + id);
		subscriptionService.deleteSubscription(id);
		return new ResponseEntity<>("Deleted subscritpion: " + id,HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<Subscription> updateSubscription(@RequestBody Subscription updatedSubscription) {
		int id = updatedSubscription.getId();
		Subscription subscription = subscriptionService.findById(id);
		if (subscription == null)
			throw new SubscriptionNotFoundException("Subscription id not found - " + id);
		subscriptionService.save(updatedSubscription);
		return new ResponseEntity<>(updatedSubscription,HttpStatus.OK);
	}
}
