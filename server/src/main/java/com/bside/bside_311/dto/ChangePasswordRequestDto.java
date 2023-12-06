package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordRequestDto {
  @Schema(example = "1a2s3d4f1!", description = "기존 비밀번호")
  private String password;
  @Schema(example = "1a2s3d4f2@", description = "신규 비밀번호")
  private String newPassword;
}
