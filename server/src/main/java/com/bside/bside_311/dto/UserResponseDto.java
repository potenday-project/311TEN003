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
  @Builder.Default
  private List<AttachDto> profileImgUrls = new ArrayList<>();

  public static UserResponseDto of(User user, List<AttachDto> userAttachDtos) {
    return UserResponseDto.builder()
                          .nickname(user.getNickname())
                          .id(user.getUserId())
                          .userNo(user.getId())
                          .introduction(user.getIntroduction())
                          .createdBy(user.getCreatedBy())
                          .profileImgUrls(userAttachDtos)
                          .build();
  }
}
