package com.subscriptiontracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.entity.User;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Value("${mail.from}")
	private String from;

	public void sendReminder(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(this.from);
		message.setTo(user.getEmail());		
		message.setSubject("Free period on your subscription/s ends tomorrow!");
		message.setText(generateText(user.getSubscriptions()));
		emailSender.send(message);
	}
	
	private String generateText(List<Subscription>subscriptions) {
		if (subscriptions.size()== 1) {
			return "Free period on "+subscriptions.get(0).getName()+" ends tomorrow. The next payment is $"+subscriptions.get(0).getPrice();
		}else {
			StringBuilder text=new StringBuilder("Free period on: ");
			for(int i=0;i<subscriptions.size();i++) {
				text.append(subscriptions.get(i).getName());
				if(i==subscriptions.size()-1) text.append(" ends tomorrow.");
				else text.append(", ");
			}			
			return text.toString();
		}
	}
	
}
