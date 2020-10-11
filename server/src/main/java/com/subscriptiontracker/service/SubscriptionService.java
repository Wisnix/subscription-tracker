package com.subscriptiontracker.service;

import java.util.List;

import com.subscriptiontracker.entity.Subscription;

public interface SubscriptionService {
	
	Subscription findById(int id);
	
	List<Subscription> findByUserId(int userId);
	
	Subscription save(Subscription subscription);
	
	void updateReminders(List<Subscription> subscriptions);
	
	void deleteSubscription(int id);
	
}
