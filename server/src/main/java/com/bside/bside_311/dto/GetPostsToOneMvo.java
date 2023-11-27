package com.bside.bside_311.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetPostsToOneMvo {
  Long postNo;
  String nickname;
  String userId;
  Boolean isFollowedByMe;
  Long userFollowNo;
  Boolean isLikedByMe;
  Long postLikeNo;
  Long alcoholNo;
  String alcoholType;
}
