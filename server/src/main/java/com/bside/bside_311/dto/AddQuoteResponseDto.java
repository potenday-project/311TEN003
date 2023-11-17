package com.bside.bside_311.dto;

import com.bside.bside_311.entity.PostQuote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class AddQuoteResponseDto {
  @Schema(example = "1", description = "게시글 인용 번호")
  private Long postQuoteNo;

  public static AddQuoteResponseDto of(PostQuote addQuote) {
    return AddQuoteResponseDto.builder()
                              .postQuoteNo(addQuote.getId())
                              .build();
  }
}
