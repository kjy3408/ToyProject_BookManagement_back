package com.toyproject.bookmanagement.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.toyproject.bookmanagement.dto.auth.JwtTokenRespDto;
import com.toyproject.bookmanagement.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
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
			stringBuilder.append(authority.getAuthority());
			stringBuilder.append(",");
		});
		
		stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length()); //쉼표제거
		
		String authorities = stringBuilder.toString();
		
		Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 *24));
		
//		PrincipalUser userDetails = (PrincipalUser) authentication.getPrincipal();
	
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
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			
			return true;
			
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
			
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			
		} catch (IllegalArgumentException e) {
			log.info("IllegalArgument JWT Token", e);
			
		} catch (Exception e) {
			log.info("JWT Token Error", e);
		}
		
		return false;
	}
	
	public String getToken(String token) {
		String type = "Bearer";
		if(StringUtils.hasText(token) && token.startsWith(type)) {
			return token.substring(type.length() + 1);
		}
		
		return null;
	}
	
	public Claims getClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Authentication getAuthentication(String accessToken) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Claims claims = getClaims(accessToken);
		
		if(claims.get("auth") == null) {
			throw new CustomException("AccessToken에 권한 정보가 없습니다.");
		}
		
		String auth = claims.get("auth").toString();
		
		for(String role :auth.split(",")) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
				
		UserDetails userDetails = new User(claims.getSubject(), "", authorities);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
		
		
		return authentication;
	}
}







