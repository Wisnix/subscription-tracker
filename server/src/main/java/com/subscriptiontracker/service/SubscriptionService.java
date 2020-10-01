package com.subscriptiontracker.service;

import java.util.List;

import com.subscriptiontracker.entity.Subscription;

public interface SubscriptionService {
	
	Subscription findById(int id);
	
	List<Subscription> findByUserId(int userId);
	
	Subscription save(Subscription subscription);
	
	void deleteSubscription(int id);
	
}
