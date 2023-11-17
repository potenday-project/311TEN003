package com.bside.bside_311.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetAlcoholResponseDto {
  private List<AlcoholResponseDto> list;
  private Long totalCount;

  public GetAlcoholResponseDto(List<AlcoholResponseDto> list, Long totalCount) {
    this.list = list;
    this.totalCount = totalCount;
  }

  public static GetAlcoholResponseDto of(List<AlcoholResponseDto> alcoholResponseDtos,
                                         Long alcoholsCount) {
    return new GetAlcoholResponseDto(alcoholResponseDtos, alcoholsCount);
  }
}
