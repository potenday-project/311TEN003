package com.bside.bside_311.dto;

import com.bside.bside_311.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class UserResponseDto {
  private String nickname;
  private String id;
  private Long userNo;
  private String introduction;
  private Long createdBy;
  private Boolean isFollowedByMe;
  private Long followedCount;
  @Builder.Default
  private List<AttachDto> profileImgUrls = new ArrayList<>();

  public static UserResponseDto of(User user) {
    return UserResponseDto.of(user, null);
  }

  public static UserResponseDto of(User user, List<AttachDto> userAttachDtos) {
    return UserResponseDto.of(user, userAttachDtos, null);
  }

  public static UserResponseDto of(User user, List<AttachDto> userAttachDtos,
                                   Boolean isFollowedByMe) {
    return UserResponseDto.builder()
                          .nickname(user.getNickname())
                          .id(user.getUserId())
                          .userNo(user.getId())
                          .introduction(user.getIntroduction())
                          .createdBy(user.getCreatedBy())
                          .isFollowedByMe(isFollowedByMe)
                          .profileImgUrls(userAttachDtos)
                          .build();
  }

  public static UserResponseDto of(UserIncludeFollowCountDto user, List<AttachDto> attachDtos,
                                   Boolean isFollowedByMe) {
    return UserResponseDto.builder()
                          .nickname(user.getNickname())
                          .id(user.getId())
                          .userNo(user.getUserNo())
                          .introduction(user.getIntroduction())
                          .createdBy(user.getCreatedBy())
                          .isFollowedByMe(isFollowedByMe)
                          .profileImgUrls(attachDtos)
                          // followedCount
                          .followedCount(user.getFollowedCount())
                          .build();
  }
}
