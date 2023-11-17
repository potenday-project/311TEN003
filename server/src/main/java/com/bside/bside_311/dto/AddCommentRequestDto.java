package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddCommentRequestDto {
  @Schema(example = "댓글 내용", description = "댓글 내용")
  private String commentContent;
}
