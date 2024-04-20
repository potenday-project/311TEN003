package com.bside.bside_311.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthDto {
	protected Long userNo;
	private String tokenType;
	private String userId;
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

	@Builder
	public AuthDto(String tokenType, Long userNo, String userId,
		Collection<? extends GrantedAuthority> authorities) {
		this.tokenType = tokenType;
		this.userNo = userNo;
		this.userId = userId;
		this.authorities = authorities;
	}
}
