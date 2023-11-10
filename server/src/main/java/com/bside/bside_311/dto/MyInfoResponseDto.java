package com.bside.bside_311.dto;

import com.bside.bside_311.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class MyInfoResponseDto {

  @Schema(example = "apple", description = "아이디(중복검사 필요)")
  String id;
  @Schema(example = "bside", description = "닉네임(중복검사 불필요)")
  String nickname;

  @Builder.Default
  List<AttachDto> profileImages = new ArrayList<>();

  @Schema(example = "안녕하세요.", description = "자기소개.")
  String introduction;

  @Schema(example = "24", description = "팔로워수")
  Long followerCount;


  public MyInfoResponseDto(MyInfoResponseDto myInfoResponseDto) {
    this.id = myInfoResponseDto.getId();
    this.nickname = myInfoResponseDto.getNickname();
    this.profileImages = myInfoResponseDto.getProfileImages();
    this.introduction = myInfoResponseDto.getIntroduction();
    this.followerCount = myInfoResponseDto.getFollowerCount();
  }

  public static MyInfoResponseDto of(User user) {
    MyInfoResponseDto myInfoResponseDto = MyInfoResponseDto.builder()
                                                           .id(user.getUserId())
                                                           .nickname(user.getNickname())
                                                           .profileImages(new ArrayList<>())
                                                           .introduction(user.getIntroduction())
                                                           .followerCount(null)
                                                           .build();
    return myInfoResponseDto;
  }
}
