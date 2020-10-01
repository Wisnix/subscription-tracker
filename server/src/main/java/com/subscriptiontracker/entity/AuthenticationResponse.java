package com.subscriptiontracker.entity;

import java.util.Date;

public class AuthenticationResponse {
	
	private String token;
	private Date expiration;

	public AuthenticationResponse(String token,Date expiration) {
		super();
		this.token = token;
		this.expiration=expiration;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
}
