package com.subscriptiontracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.subscriptiontracker.entity.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
	
	List<Subscription> findByUserId(Integer userId);
}
