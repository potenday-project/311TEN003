package com.bside.bside_311.dto;

import com.bside.bside_311.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetUserInfoResponseDto extends MyInfoResponseDto {

  @Schema(example = "true", description = "조회자가 대상자를 팔로워하는 지 여부")
  private Boolean isFollowing;

  public GetUserInfoResponseDto(Long userNo, String id, String nickname,
                                List<AttachDto> profileImages,
                                String introduction, Long followerCount, Long followingCount,
                                Boolean isFollowing) {
    super(userNo, id, nickname, profileImages, introduction, followerCount, followingCount);
    this.isFollowing = isFollowing;
  }

  public GetUserInfoResponseDto(MyInfoResponseDto myInfoResponseDto, Boolean isFollowing) {
    super(myInfoResponseDto);
    this.isFollowing = isFollowing;
  }

  public static GetUserInfoResponseDto of(User user) {
    MyInfoResponseDto myInfoResponseDto = MyInfoResponseDto.builder()
                                                           .id(user.getUserId())
                                                           .nickname(user.getNickname())
                                                           .profileImages(new ArrayList<>())
                                                           .introduction(user.getIntroduction())
                                                           .followerCount(null)
                                                           .build();
    return new GetUserInfoResponseDto(myInfoResponseDto, null);
  }

  public static GetUserInfoResponseDto of(MyInfoResponseDto userInfoResponseDto,
                                          Boolean isFollowing) {
    return new GetUserInfoResponseDto(userInfoResponseDto, isFollowing);
  }
}
