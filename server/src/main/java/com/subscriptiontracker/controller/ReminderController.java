package com.subscriptiontracker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.subscriptiontracker.entity.User;
import com.subscriptiontracker.service.EmailService;
import com.subscriptiontracker.service.SubscriptionService;
import com.subscriptiontracker.service.UserService;

@Controller
public class ReminderController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SubscriptionService subscriptionService;


	@Scheduled(cron="0 * 12 * * *")
	public void sendReminders() {
		Set<User> users = userService.findAllUsersWithActiveRemainder();
		users.stream().forEach(user -> {
			emailService.sendReminder(user);
			subscriptionService.updateReminders(user.getSubscriptions());
		});
	}

}
