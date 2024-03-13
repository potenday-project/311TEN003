package com.bside.bside_311.model;

import com.bside.bside_311.entity.Role;

import java.util.Collection;

public class TestUserAuthInfo implements AbstractUserAuthInfo {
  private long id;
  private Collection<Role> authorities;

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
