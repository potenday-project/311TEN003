package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MyInfoResponseDto {

  @Schema(example = "apple", description = "아이디(중복검사 필요)")
  String id;
  @Schema(example = "bside", description = "닉네임(중복검사 불필요)")
  String nickname;

  @Schema(example = "http://www.naver.com", description = "프로필 사진 링크.")
  String profileImageLink;

  @Schema(example = "안녕하세요.", description = "자기소개.")
  String introduction;

  @Schema(example = "24", description = "팔로워수")
  Integer followerCount;

}
