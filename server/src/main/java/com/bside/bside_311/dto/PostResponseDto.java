package com.bside.bside_311.dto;


import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class PostResponseDto {
  private String nickname;
  private String id;
  @Schema(example = "[\"www.naver.com\", \"www.daum.net\"]", description = "프로필 이미지 URL")
  @Builder.Default
  private List<AttachDto> profileImgUrls = new ArrayList<>();
  private boolean isFollowedByMe;
  private boolean isLikedByMe;
  private LocalDateTime updateDt;
  private boolean edited;
  private Long postNo;
  private String postContent;
  @Schema(example = "10", description = "댓글 개수.")
  private Long commentCount;
  private String positionInfo;

  private Long alcoholNo;
  private String alcoholType;
  private String alcoholName;
  @Builder.Default
  private List<AttachDto> postAttachUrls = new ArrayList<>();
  @Builder.Default
  private List<String> tagList = new ArrayList<>();
  @Builder.Default
  private List<QuoteInfoDto> quoteInfo = new ArrayList<>();

  private Long likeCount;

  private Long quoteCount;

  public static PostResponseDto of(Post post, User user, Alcohol alcohol, List<Tag> tags,
                                   List<Comment> comments, List<AttachDto> attachDtos
      , Boolean isLikedByMe, Boolean isFollowedByMe, Long likeCount, Long quoteCount) {
    PostResponseDtoBuilder postResponseDtoBuilder = PostResponseDto.builder();
    if (user != null) {
      postResponseDtoBuilder.nickname(user.getNickname()).id(user.getUserId());
    }
    if (alcohol != null) {
      postResponseDtoBuilder.alcoholNo(alcohol.getId())
                            .alcoholName(alcohol.getName())
                            .alcoholType(alcohol.getAlcoholType().getName());
    }
    if (CollectionUtils.isNotEmpty(tags)) {
      List<String> tagList = tags.stream().map(Tag::getName).toList();
      postResponseDtoBuilder.tagList(tagList);
    }
    // TODO quoteInfo는 나중에.
    if (ObjectUtils.isNotEmpty(attachDtos)) {
      postResponseDtoBuilder.postAttachUrls(attachDtos);
    }

    if (ObjectUtils.isNotEmpty(isFollowedByMe)) {
      postResponseDtoBuilder.isFollowedByMe(isFollowedByMe);
    }
    if (ObjectUtils.isNotEmpty(isLikedByMe)) {
      postResponseDtoBuilder.isLikedByMe(isLikedByMe);
    }
    if (ObjectUtils.isNotEmpty(likeCount)) {
      postResponseDtoBuilder.likeCount(likeCount);
    }
    if (ObjectUtils.isNotEmpty(quoteCount)) {
      postResponseDtoBuilder.quoteCount(quoteCount);
    }

    // END
    return postResponseDtoBuilder
               .updateDt(post.getLastModifiedDate())
               .edited(post.getLastModifiedBy() != null ? true : false)
               .postNo(post.getId())
               .postContent(post.getContent())
               .commentCount((long) comments.size())
               .positionInfo(post.getPosition()).build();


  }
}
