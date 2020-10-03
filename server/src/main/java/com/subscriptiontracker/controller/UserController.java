package com.subscriptiontracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subscriptiontracker.entity.AuthenticationRequest;
import com.subscriptiontracker.entity.AuthenticationResponse;
import com.subscriptiontracker.entity.Subscription;
import com.subscriptiontracker.entity.User;
import com.subscriptiontracker.error.UserAlreadyExistsException;
import com.subscriptiontracker.service.SubscriptionService;
import com.subscriptiontracker.service.UserService;
import com.subscriptiontracker.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwt;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/{id}/subscriptions")
	public List<Subscription> findSubscriptionsByUserId(@PathVariable Integer id) {
		return subscriptionService.findByUserId(id);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		if(userService.findByEmail(user.getEmail())!=null) {
			throw new UserAlreadyExistsException("User with Email "+user.getEmail()+" already exists.");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.createUser(user);
		return new ResponseEntity<>("User created.", HttpStatus.CREATED);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> generateToken(@RequestBody AuthenticationRequest request) throws Exception {
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid Email/password",e);
		}
		final UserDetails userDetails=userService.loadUserByUsername(request.getEmail());
		AuthenticationResponse authRes=new AuthenticationResponse(jwt.generateToken(userDetails),jwt.getExpirationDate());
		return new ResponseEntity<>(authRes,HttpStatus.OK);
	}

}
