package com.bside.bside_311.dto;

import com.bside.bside_311.entity.PostQuote;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class GetQuotesByPostResponseDto {
  List<QuoteInfoDto> list;
  Long totalCount;

  public static GetQuotesByPostResponseDto of(List<PostQuote> postQuotes) {
    List<QuoteInfoDto> list = postQuotes.stream()
                                        .map(QuoteInfoDto::of)
                                        .collect(Collectors.toList());
    Long totalCount = (long) list.size();
    return new GetQuotesByPostResponseDto(list, totalCount);
  }
}
