package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentResponseDto {
  private Long commentNo;
  private String commentContent;

  public static CommentResponseDto of(Comment comment) {
    return new CommentResponseDto(comment.getId(), comment.getContent());
  }
}
