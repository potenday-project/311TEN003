package com.bside.bside_311.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostResponseDto {
  private String nickname;
  private String id;
  @Schema(example = "[\"www.naver.com\", \"www.daum.net\"]",description = "프로필 이미지 URL")
  private List<String> profileImgUrls;
  private boolean isFollowedByMe;
  private boolean isLikedByMe;
  private LocalDateTime updateDt;
  private boolean edited;
  private Long postNo;
  private String postContent;
  private String positionInfo;
  private String alcoholName;
  private List<String> postAttachUrls;
  private List<String> tagList;
  private List<QuoteInfoDto> quoteInfo;
  private Long likeCount;
  private Long quoteCount;
}
