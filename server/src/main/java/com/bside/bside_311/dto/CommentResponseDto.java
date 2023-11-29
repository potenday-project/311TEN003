package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class CommentResponseDto {
  private Long commentNo;
  private String commentContent;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
  private Long createdBy;
  private String userId;
  private String nickname;
  @Builder.Default
  private List<AttachDto> profileImgUrls = new ArrayList<>();

  public static CommentResponseDto of(Comment comment, User createUser,
                                      List<AttachDto> profileImgUrls) {
    return CommentResponseDto.builder()
                             .commentNo(comment.getId())
                             .commentContent(comment.getContent())
                             .createdDate(comment.getCreatedDate())
                             .lastModifiedDate(comment.getLastModifiedDate())
                             .createdBy(comment.getCreatedBy())
                             .userId(createUser.getUserId()).nickname(createUser.getNickname())
                             .profileImgUrls(profileImgUrls).build();
  }
}
