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
}
