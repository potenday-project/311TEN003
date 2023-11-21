package com.bside.bside_311.utils;

import com.bside.bside_311.dto.UserSignupRequestDto;

public class UserUtils {
  public static UserSignupRequestDto makeSimpleUser() {
    String email = "newbie@example.com";
    String password = "password";
    String id = "newbie";
    String name = "Newbie";
    return UserSignupRequestDto.builder()
                               .email(email)
                               .password(password)
                               .id(id)
                               .nickname(name)
                               .build();
  }


}
