package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttachDto {
  @Schema(example = "1", description = "첨부 번호")
  private Integer attachNo;
  @Schema(example = "http://www.naver.com", description = "프로필 사진 링크.")
  private String attachUrl;
  @Schema(example = "profile", description = "첨부 타입")
  private String attachType;
}
