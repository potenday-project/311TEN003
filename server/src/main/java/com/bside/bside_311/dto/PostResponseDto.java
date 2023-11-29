package com.bside.bside_311.dto;


import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
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
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class PostResponseDto {
  private String nickname;
  private String id;
  private Long createdBy;
  @Schema(example = "[\"www.naver.com\", \"www.daum.net\"]", description = "프로필 이미지 URL")
  @Builder.Default
  private List<AttachDto> profileImgUrls = new ArrayList<>();
  private boolean isFollowedByMe;
  private boolean isLikedByMe;
  private LocalDateTime lastModifiedDate;
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
               .createdBy(post.getCreatedBy())
               .lastModifiedDate(post.getLastModifiedDate())
               .edited(
                   post.getCreatedDate().isEqual(post.getLastModifiedDate()) ? false : true)
               .postNo(post.getId())
               .postContent(post.getContent())
               .commentCount((long) comments.size())
               .positionInfo(post.getPosition()).build();


  }

  public static PostResponseDto of(Post post, GetPostsToOneMvo getPostsToOneMvo,
                                   List<AttachDto> postAttachDtos, List<AttachDto> userAttachDtos) {
    List<PostTag> postTags = post.getPostTags().stream()
                                 .filter(postTag -> YesOrNo.N == postTag.getDelYn())
                                 .collect(Collectors.toList());
    List<String> tagList = postTags.stream()
                                   .filter(postTag -> YesOrNo.N == postTag.getTag().getDelYn())
                                   .map(postTag -> postTag.getTag().getName()).toList();
    PostResponseDto postResponseDto =
        PostResponseDto.builder().nickname(getPostsToOneMvo.getNickname())
                       .id(getPostsToOneMvo.getUserId())
                       .createdBy(post.getCreatedBy())
                       .isFollowedByMe(getPostsToOneMvo.getIsFollowedByMe())
                       .isLikedByMe(getPostsToOneMvo.getIsLikedByMe())
                       .lastModifiedDate(post.getLastModifiedDate())
                       .edited(post.getCreatedDate()
                                   .isEqual(post.getLastModifiedDate()) ?
                                   false : true)
                       .postNo(post.getId())
                       .postContent(post.getContent())
                       .commentCount((long) post.getComments().stream().filter(
                                                    comment -> YesOrNo.N == comment.getDelYn()).toList()
                                                .size())
                       .positionInfo(post.getPosition())
                       .alcoholNo(getPostsToOneMvo.getAlcoholNo())
                       .alcoholType(getPostsToOneMvo.getAlcoholType())
                       .alcoholName(getPostsToOneMvo.getAlcoholType())
                       .postAttachUrls(postAttachDtos)
                       .profileImgUrls(userAttachDtos)
                       .tagList(tagList)
                       .likeCount(
                           (long) post.getPostLikes().stream().filter(
                                          postLike -> YesOrNo.N == postLike.getDelYn())
                                      .collect(
                                          Collectors.toList()).size())
                       .quoteCount((long) post.getPostQuoteEds().stream()
                                              .filter(
                                                  postQuoted -> YesOrNo.N == postQuoted.getDelYn())
                                              .collect(
                                                  Collectors.toList()).size())
                       .build();
    return postResponseDto;
  }
}
