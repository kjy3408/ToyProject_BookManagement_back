package com.toyproject.bookmanagement.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.toyproject.bookmanagement.dto.auth.JwtTokenRespDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//		Decoders.BASE64.decode(secretKey);
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public JwtTokenRespDto makeToken(Authentication authentication) {
		StringBuilder stringBuilder = new StringBuilder();
		
		authentication.getAuthorities().forEach(authority -> {
			stringBuilder.append(authority.getAuthority() + ",");
//			stringBuilder.append(",");
		});
		
		stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length()); //쉼표제거
		
		String authorities = stringBuilder.toString();
		
		Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 *24));
		
		PrincipalUser userDetails = (PrincipalUser) authentication.getPrincipal();
	
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(tokenExpiresDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return JwtTokenRespDto.builder()
				.grantType("Bearer")
				.accessToken(accessToken).build();
	}
}
