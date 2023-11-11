package com.bside.bside_311.dto;

import com.bside.bside_311.entity.UserFollow;
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
public class UserFollowResponseDto {
  @Schema(example = "1", description = "유저 팔로우 번호")
  private Long userFollowNo;

  public static UserFollowResponseDto of(UserFollow userFollow) {
    return new UserFollowResponseDto(userFollow.getId());
  }
}
