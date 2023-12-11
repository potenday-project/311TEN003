package com.bside.bside_311.dto;

import lombok.Data;

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
  String alcoholName;
}
