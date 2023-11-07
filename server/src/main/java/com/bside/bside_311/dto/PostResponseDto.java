package com.bside.bside_311.dto;


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
  private boolean isFollowedByMe;
  private LocalDateTime updateDt;
  private boolean edited;
  private Integer postNo;
  private String postContent;
  private String positionInfo;
  private String alcoholName;
  private List<String> postAttachUrl;
  private List<String> tagList;
  private List<QuoteInfoDto> quoteInfo;
  private Integer likeCount;
  private Integer quoteCount;
}
