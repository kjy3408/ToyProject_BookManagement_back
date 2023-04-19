package com.toyproject.bookmanagement.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {

	private int id;
	private String email;
}
