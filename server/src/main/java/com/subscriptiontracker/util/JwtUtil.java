package com.subscriptiontracker.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String KEY;
	
	@Value("${jwt.expiration-time}")
	private long expirationTime;
	
	public String generateToken(UserDetails userDetails,int userId) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("userId", userId);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*expirationTime))
				.signWith(SignatureAlgorithm.HS256,KEY).compact();
	}
	
	public Boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return(username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token,Function<Claims,T>claimsResolver) {
		final Claims claims=extractlAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractlAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		 return extractClaim(token, Claims::getSubject);
	}
	
	public Integer extractUserId(String token) {
		 return (Integer) extractClaim(token, c->c.get("userId"));
	}

	public Date getExpirationDate() {
		return new Date(System.currentTimeMillis()+expirationTime*1000);
	}
	
}
