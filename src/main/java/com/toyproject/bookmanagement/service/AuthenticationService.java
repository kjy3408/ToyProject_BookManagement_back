package com.toyproject.bookmanagement.service;

import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.auth.SignupReqDto;
import com.toyproject.bookmanagement.entity.User;
import com.toyproject.bookmanagement.exception.CustomException;
import com.toyproject.bookmanagement.exception.ErrorMap;
import com.toyproject.bookmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

	private final UserRepository userRepository; 
	
	
	public void checkDuplicatedEmail(String email) {
		
		User userEmail = userRepository.findUserByEmail(email);
		
		if(userEmail != null) {
			
			throw new CustomException("Duplicated Eamil", ErrorMap.builder().put("email", "사용중인 이메일입니다.").build());
		}
	}
	
	public void signup(SignupReqDto signupReqDto) {
		
		userRepository.saveUser(signupReqDto.toEntity());
	}
}
