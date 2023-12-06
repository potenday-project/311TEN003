package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class PostSearchCondition {
  @Schema(description = "키워드", example = "키워드")
  String searchKeyword;
  @Schema(description = "검색 유저 번호들.", example = "1,2,4")
  List<Long> searchUserNoList;
  @Schema(description = "나에 의해서 좋아하는 게시글 필터 여부(true or false).", example = "false")
  Boolean isLikedByMe;
  @Schema(description = "내가 댓글을 단 게시글 필터 여부.(true or false)", example = "false")
  Boolean isCommentedByMe;
  @Schema(description = "나의 유저 정보.(null, or 1L, 2L ...)", example = "1")
  Long myUserNo;
}
