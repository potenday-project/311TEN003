package com.bside.bside_311.model;

import java.util.Collection;

import com.bside.bside_311.entity.Role;

public class TestUserAuthInfo implements AbstractUserAuthInfo {
	private final long id;
	private final Collection<Role> authorities;

	public TestUserAuthInfo(long id, Collection<Role> authorities) {
		this.id = id;
		this.authorities = authorities;
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
