package com.subscriptiontracker.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.entity.User;
import com.subscriptiontracker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found - "+username);
		}
		return createSpringUserFromEntity(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	private org.springframework.security.core.userdetails.User createSpringUserFromEntity(User user) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new ArrayList<>());
	}
	
	public void createUser(User user) {
		userRepository.save(user);
	}
	
	public Set<User> findAllUsersWithActiveRemainder(){
		Set<User> users=userRepository.findAllUsersWithActiveRemainder();
		LocalDate today = LocalDate.now();
		
		Predicate<Subscription> isPaymentTomorrow = (subscription -> {
			LocalDate endOfFreePeriod = subscription.getStartDate().plusDays(subscription.getFreePeriod());
			return Period.between(today, endOfFreePeriod).getDays() != 1;
		});
		
		return users.stream().map(user -> {
			user.getSubscriptions().removeIf(isPaymentTomorrow);
			return user;
		}).filter(user -> user.getSubscriptions().size() != 0).collect(Collectors.toSet());		
	}

}
