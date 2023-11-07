package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

}
