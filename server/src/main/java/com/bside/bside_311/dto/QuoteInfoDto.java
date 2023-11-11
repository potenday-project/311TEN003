package com.bside.bside_311.dto;

import com.bside.bside_311.entity.PostQuote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class QuoteInfoDto {
  @Schema(example = "1", description = "인용 번호")
  private Long quoteNo;
  @Schema(example = "1", description = "인용한 게시글 내용")
  private String quoteContent;

  public static QuoteInfoDto of(PostQuote postQuote) {
    return new QuoteInfoDto(postQuote.getId(), "");
  }
}
