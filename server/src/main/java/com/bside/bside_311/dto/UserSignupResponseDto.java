package com.bside.bside_311.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSignupResponseDto {
  private Long userNo;

  public UserSignupResponseDto(Long userNo) {
    this.userNo = userNo;
  }
  public static UserSignupResponseDto of(Long userNo){
    return new UserSignupResponseDto(userNo);
  }
}
