package com.toyproject.bookmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.httpBasic().disable();
		http.formLogin().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션사용X (무상태성)
		
		http.authorizeHttpRequests()
		.antMatchers("/auth/**")
		.permitAll() //"/auth"요청은 다 허용해라
		.anyRequest()
		.permitAll();
//		.authenticated(); //나머지는 인증 거쳐라
	}
}
