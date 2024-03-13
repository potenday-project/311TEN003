package com.bside.bside_311.model;

import com.bside.bside_311.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;

import java.util.Collection;

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
