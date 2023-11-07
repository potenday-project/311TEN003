package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EditPostRequestDto {
  @Schema(example = "1", description = "선택 주류 번호")
  private Integer alcoholNo;
  @Schema(example = "게시글 내용", description = "게시글 내용")
  private String postContent;

  @Schema(example = "[\"test\", \"외로울때\", \"파스타\"", description = "태그 리스트. 해당 문자열 없을경우 데이터 생성.")
  private List<String> tagList;
}
