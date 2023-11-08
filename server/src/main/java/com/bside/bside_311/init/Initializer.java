package com.bside.bside_311.init;

import com.bside.bside_311.controller.UserController;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {
  private final UserController userController;

  @PostConstruct
  public void init() {
    for (int i = 0; i < 5; i++) {
      userController.signup(UserSignupRequestDto.builder()
                                                .email(String.format("test%d@example.com", i))
                                                .password("1a2s3d4f1!")
                                                .id(String.format("test%d", i))
                                                .nickname(String.format("bside%d", i))
                                                .build());
    }

  }
}
