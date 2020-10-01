package com.subscriptiontracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository repository;

	@Override
	public Subscription findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Subscription> findByUserId(int userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public Subscription save(Subscription subscription) {
		return repository.save(subscription);
	}

	@Override
	public void deleteSubscription(int id) {
		repository.deleteById(id);
	}
	
	

}
