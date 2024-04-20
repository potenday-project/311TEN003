package com.bside.bside_311.model;

import java.util.Collection;

import com.bside.bside_311.entity.Role;

public interface AbstractUserAuthInfo {
	long getId();

	Collection<Role> getAuthorities();
}
