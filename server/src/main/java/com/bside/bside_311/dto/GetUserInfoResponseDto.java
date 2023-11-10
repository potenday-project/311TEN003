package com.bside.bside_311.dto;

import com.bside.bside_311.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetUserInfoResponseDto extends MyInfoResponseDto{

  public GetUserInfoResponseDto(String id, String nickname, List<AttachDto> profileImages,
                                String introduction, Integer followerCount, Boolean isFollowing) {
    super(id, nickname, profileImages, introduction, followerCount);
    this.isFollowing = isFollowing;
  }

  public GetUserInfoResponseDto(MyInfoResponseDto myInfoResponseDto, Boolean isFollowing) {
    super(myInfoResponseDto);
    this.isFollowing = isFollowing;
  }

  @Schema(example = "true", description = "조회자가 대상자를 팔로워하는 지 여부")
  private Boolean isFollowing;

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
}
