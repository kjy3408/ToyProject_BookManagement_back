package com.toyproject.bookmanagement.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrincipalRespDto {
	private int userId;
	private String email;
	private String name;
	private String authorities;
	
}
