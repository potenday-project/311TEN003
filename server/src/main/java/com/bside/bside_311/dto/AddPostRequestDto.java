package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class AddPostRequestDto {
  @Schema(example = "1", description = "선택 주류 번호")
  private Long alcoholNo;
  @Schema(example = "산뜻함. 달콤함.", description = "주류 특징")
  private String alcoholFeature;
  @Schema(example = "게시글 내용", description = "게시글 내용")
  private String postContent;
  @Schema(example = "BASIC", description = "게시글 타입")
  private String postType;
  @Schema(example = "위치 정보", description = "위치에 대한 정보")
  private String positionInfo;


  @Builder.Default
  @Schema(description = "태그 리스트. 해당 문자열 없을경우 데이터 생성.")
  private List<String> tagList = new ArrayList<>();
}
