package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {
  @Schema(example = "닉네임.", description = "닉네임.")
  String nickname;

  @Schema(example = "안녕하세요.", description = "자기소개.")
  String introduction;
}
