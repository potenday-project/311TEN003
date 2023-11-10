package com.bside.bside_311.dto;


import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class PostResponseDto {
  private String nickname;
  private String id;
  @Schema(example = "[\"www.naver.com\", \"www.daum.net\"]", description = "프로필 이미지 URL")
  @Builder.Default
  private List<String> profileImgUrls = new ArrayList<>();
  private boolean isFollowedByMe;
  private boolean isLikedByMe;
  private LocalDateTime updateDt;
  private boolean edited;
  private Long postNo;
  private String postContent;
  private String positionInfo;

  private Long alcoholNo;
  private String alcoholName;
  @Builder.Default
  private List<String> postAttachUrls = new ArrayList<>();
  @Builder.Default
  private List<String> tagList = new ArrayList<>();
  @Builder.Default
  private List<QuoteInfoDto> quoteInfo = new ArrayList<>();

  private Long likeCount;

  private Long quoteCount;

  public PostResponseDto(String nickname, String id, List<String> profileImgUrls,
                         boolean isFollowedByMe, boolean isLikedByMe, LocalDateTime updateDt,
                         boolean edited, Long postNo, String postContent, String positionInfo,
                         Long alcoholNo, String alcoholName, List<String> postAttachUrls,
                         List<String> tagList, List<QuoteInfoDto> quoteInfo, Long likeCount,
                         Long quoteCount) {
    this.nickname = nickname;
    this.id = id;
    this.profileImgUrls = profileImgUrls;
    this.isFollowedByMe = isFollowedByMe;
    this.isLikedByMe = isLikedByMe;
    this.updateDt = updateDt;
    this.edited = edited;
    this.postNo = postNo;
    this.postContent = postContent;
    this.positionInfo = positionInfo;
    this.alcoholNo = alcoholNo;
    this.alcoholName = alcoholName;
    this.postAttachUrls = postAttachUrls;
    this.tagList = tagList;
    this.quoteInfo = quoteInfo;
    this.likeCount = likeCount;
    this.quoteCount = quoteCount;
  }

  public static PostResponseDto of(Post post, User user, Alcohol alcohol, List<Tag> tags) {
    PostResponseDtoBuilder postResponseDtoBuilder = PostResponseDto.builder();
    if (user != null) {
      postResponseDtoBuilder.nickname(user.getNickname()).id(user.getUserId());
    }
    if (alcohol != null) {
      postResponseDtoBuilder.alcoholNo(alcohol.getId())
                            .alcoholName(alcohol.getName());
    }
    if (CollectionUtils.isNotEmpty(tags)) {
      List<String> tagList = tags.stream().map(Tag::getName).toList();
      postResponseDtoBuilder.tagList(tagList);
    }
    // TODO
    // profileImgUrls, isFollowedByMe, isLikedByMe, postAttachUrls, quoteInfo, likeCount, quoteCount
    // 가 반환되지 않음. 유의할것. 해야함. 나중에. 언제?

    // END
    return postResponseDtoBuilder
               .updateDt(post.getLastModifiedDate())
               .edited(post.getLastModifiedBy() != null ? true : false)
               .postNo(post.getId())
               .postContent(post.getContent())
               .positionInfo(post.getPosition()).build();


  }
}
