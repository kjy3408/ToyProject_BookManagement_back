package com.toyproject.bookmanagement.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtTokenRespDto {

	private String grantType;
	private String accessToken;
}
