package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class MyInfoResponseDto {

  @Schema(example = "apple", description = "아이디(중복검사 필요)")
  String id;
  @Schema(example = "bside", description = "닉네임(중복검사 불필요)")
  String nickname;

  List<AttachDto> profileImages;

  @Schema(example = "안녕하세요.", description = "자기소개.")
  String introduction;

  @Schema(example = "24", description = "팔로워수")
  Integer followerCount;

  @Builder
  public MyInfoResponseDto(String id, String nickname, List<AttachDto> profileImages,
                           String introduction, Integer followerCount) {
    this.id = id;
    this.nickname = nickname;
    this.profileImages = profileImages;
    this.introduction = introduction;
    this.followerCount = followerCount;
  }

  public MyInfoResponseDto(MyInfoResponseDto myInfoResponseDto) {
    this.id = myInfoResponseDto.getId();
    this.nickname = myInfoResponseDto.getNickname();
    this.profileImages = myInfoResponseDto.getProfileImages();
    this.introduction = myInfoResponseDto.getIntroduction();
    this.followerCount = myInfoResponseDto.getFollowerCount();
  }
}
