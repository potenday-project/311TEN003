package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserLoginRequestDto {
  @Schema(example = "efxfawef", description = "패스워드8~20자 대소문자, 숫자, 특수기호)")
  @NotBlank
  String password;
  @Schema(example = "apple", description = "아이디(중복검사 필요)")
  String id;
}
