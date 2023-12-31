package com.bside.bside_311.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class UserSignupRequestDto {
  @Schema(example = "test@example.com", description = "유저 이메일")
  @NotBlank
  @Email
  String email;
  @Schema(example = "1a2s3d4f1!", description = "패스워드8~20자 대소문자, 숫자, 특수기호)")
  @NotBlank
  String password;
  @Schema(example = "apple", description = "아이디(중복검사 필요)")
  @NotBlank
  String id;
  @Schema(example = "bside", description = "닉네임(중복검사 불필요)")
  @NotBlank
  String nickname;

  @Schema(example = "자기소개입니다", description = "자기소개 이다.")
  String introduction;
}
