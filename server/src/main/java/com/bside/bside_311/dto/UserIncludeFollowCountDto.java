package com.bside.bside_311.dto;

import com.bside.bside_311.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserIncludeFollowCountDto {
  private String nickname;
  private String id;
  private Long userNo;
  private String introduction;
  private Long createdBy;
  private Boolean isFollowedByMe;
  private Long followedCount;

  public static UserIncludeFollowCountDto of(User user, Long followedCount) {
    return UserIncludeFollowCountDto.builder()
                                    .nickname(user.getNickname())
                                    .id(user.getUserId())
                                    .userNo(user.getId())
                                    .introduction(user.getIntroduction())
                                    .createdBy(user.getCreatedBy())
                                    .followedCount(followedCount)
                                    .build();
  }
}
