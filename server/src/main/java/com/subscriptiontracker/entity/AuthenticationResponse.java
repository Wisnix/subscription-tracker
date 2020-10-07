package com.subscriptiontracker.entity;

import java.util.Date;

public class AuthenticationResponse {
	
	private Integer userId;
	private String token;
	private Date expiration;

	public AuthenticationResponse(String token,Date expiration,Integer userId) {
		super();
		this.token = token;
		this.expiration=expiration;
		this.userId=userId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
