package com.bside.bside_311.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthDto {
  private String tokenType;
  protected Integer userNo;
  private String userId;
  private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

  @Builder
  public AuthDto(String tokenType, Integer userNo, String userId,
                 Collection<? extends GrantedAuthority> authorities) {
    this.tokenType = tokenType;
    this.userNo = userNo;
    this.userId = userId;
    this.authorities = authorities;
  }
}
