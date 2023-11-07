package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuoteInfoDto {
  @Schema(example = "1", description = "인용 번호")
  private Integer quoteNo;
  @Schema(example = "1", description = "인용한 게시글 내용")
  private String quoteContent;
}
