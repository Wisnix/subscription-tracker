package com.subscriptiontracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.error.ForbiddenException;
import com.subscriptiontracker.error.SubscriptionNotFoundException;
import com.subscriptiontracker.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Subscription> getSubscription(@PathVariable int id,@RequestAttribute int userId){
		Subscription subscription = subscriptionService.findById(id);		
		if (subscription == null)
			throw new SubscriptionNotFoundException("Subscription id not found - " + id);
		else if(subscription.getUserId()!=userId)
			throw new ForbiddenException("You are not allowed to delete this resource");
		
		return new ResponseEntity<>(subscription,HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription,
			@RequestAttribute int userId) {
		subscription.setUserId(userId);
		Subscription savedSubscription = subscriptionService.save(subscription);
		return new ResponseEntity<>(savedSubscription, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubscription(@PathVariable int id,@RequestAttribute int userId) throws Exception {		
		Subscription subscription = subscriptionService.findById(id);		
		if (subscription == null)
			throw new SubscriptionNotFoundException("Subscription id not found - " + id);
		else if(subscription.getUserId()!=userId)
			throw new ForbiddenException("You are not allowed to delete this resource");
		
		subscriptionService.deleteSubscription(id);
		return new ResponseEntity<>("Deleted subscritpion: " + id, HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Subscription> updateSubscription(@RequestBody Subscription updatedSubscription,@PathVariable int id, @RequestAttribute int userId) throws Exception {
		Subscription subscription = subscriptionService.findById(id);
		
		if(subscription!=null && subscription.getUserId()!=userId) {
			throw new ForbiddenException("You are not allowed to delete this resource");
		}
		updatedSubscription.setId(id);
		updatedSubscription.setUserId(userId);
		subscriptionService.save(updatedSubscription);
		return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
	}
}
