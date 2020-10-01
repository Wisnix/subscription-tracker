package com.subscriptiontracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.subscriptiontracker.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);

}
