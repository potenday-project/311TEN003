package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class UserLoginRequestDto {
  @Schema(example = "apple", description = "아이디")
  @NotBlank
  String id;

  @Schema(example = "1a2s3d4f1!", description = "패스워드8~20자 대소문자, 숫자, 특수기호)")
  @NotBlank
  String password;

}
