package com.bside.bside_311.model;

import com.bside.bside_311.entity.Role;

import java.util.Collection;

public interface AbstractUserAuthInfo {
  long getId();

  Collection<Role> getAuthorities();
}
