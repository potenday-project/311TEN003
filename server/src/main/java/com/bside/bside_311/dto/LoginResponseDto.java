package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.bside.bside_311.util.JwtUtil.BEARER_PREFIX;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginResponseDto {
  @Schema(description = "로그인을 위한 토큰 정보 Beaerer {token}")
  private String token;

  public LoginResponseDto(String token) {
    this.token = token;
  }

  public static LoginResponseDto of(String token) {
    return new LoginResponseDto(BEARER_PREFIX + token);
  }
}
