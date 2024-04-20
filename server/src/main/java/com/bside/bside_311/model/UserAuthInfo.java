package com.bside.bside_311.model;

import java.util.Collection;

import org.springframework.security.core.Authentication;

import com.bside.bside_311.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserAuthInfo implements AbstractUserAuthInfo {
	private long id;
	private Collection<Role> authorities;

	public static UserAuthInfo of(Authentication authentication) {
		return UserAuthInfo.builder()
			.id(Long.parseLong(authentication.getName()))
			.authorities(authentication.getAuthorities().stream()
				.map(o -> Role.valueOf(o.getAuthority()))
				.toList())
			.build();
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Collection<Role> getAuthorities() {
		return authorities;
	}
}
