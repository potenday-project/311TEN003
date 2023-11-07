package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetUserInfoResponseDto extends MyInfoResponseDto{
  @Schema(example = "true", description = "조회자가 대상자를 팔로워하는 지 여부")
  private boolean isFollowing;
}
