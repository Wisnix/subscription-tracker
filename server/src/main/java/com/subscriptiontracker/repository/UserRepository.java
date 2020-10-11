package com.subscriptiontracker.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.subscriptiontracker.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findByEmail(String username);
	
	@Query("SELECT u FROM User AS u JOIN FETCH u.subscriptions as s where s.reminder=true and s.freePeriod is not null and s.freePeriod>0")
	Set<User>findAllUsersWithActiveRemainder();

}
