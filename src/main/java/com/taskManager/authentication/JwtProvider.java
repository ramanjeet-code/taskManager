package com.taskManager.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String genrateTokan(Authentication auth) {
		System.out.println("auth"+auth.getName());
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+846000000))
				.claim("username", auth.getName())
				.signWith(key).compact();
		return jwt;
	}
	
	public String getUsernameFromToakn(String jwt) {
		jwt = jwt.substring(7);
		
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		System.out.println("claims"+claims);
		String username = String.valueOf(claims.get("username"));
		
		return username;
	}
}
