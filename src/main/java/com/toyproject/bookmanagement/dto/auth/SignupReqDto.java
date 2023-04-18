package com.toyproject.bookmanagement.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class SignupReqDto {
	
	@Email
	private String email;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", message ="8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;
	
	@Pattern(regexp = "^[가-힣]*${2,7}", message = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)")
	private String name;
}
