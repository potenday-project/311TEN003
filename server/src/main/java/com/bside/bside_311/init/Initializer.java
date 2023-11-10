package com.bside.bside_311.init;

import com.bside.bside_311.controller.AlcoholController;
import com.bside.bside_311.controller.UserController;
import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Initializer {
  private final UserController userController;
  private final AlcoholController alcoholController;

  @PostConstruct
  public void init() {
    for (int i = 0; i < 5; i++) {
      userController.signup(UserSignupRequestDto.builder()
                                                .email(String.format("test%d@example.com", i))
                                                .password("1a2s3d4f1!")
                                                .id(String.format("test%d", i))
                                                .nickname(String.format("bside%d", i))
                                                .build());
      alcoholController.
          addAlcohol(AddAlcoholRequestDto.builder()
                                         .alcoholName(String.format("test%d", i))
                                         .nickNames(
                                            List.of(String.format("testNickName%d", i), String.format("testNickName23453%d", i)))
                                         .manufacturer(String.format("testNmanufacturer%d", i))
                                         .description(String.format("testDescription%d", i))
                                         .degree((float) (i + 0.5))
                                        .period(20 + i)
                                        .productionYear(1990 + i)
                                        .volume(700 + i).build());
    }

  }
}
