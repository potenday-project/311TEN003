package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Alcohol;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetAlcoholResponseDto {
  private List<AlcoholResponseDto> list;
  private Long totalCount;

  public GetAlcoholResponseDto(List<AlcoholResponseDto> list, Long totalCount) {
    this.list = list;
    this.totalCount = totalCount;
  }


  public static GetAlcoholResponseDto of(List<Alcohol> alcohols, Long totalCount) {
    List<AlcoholResponseDto> list = alcohols.stream().map(alcohol -> AlcoholResponseDto.of(alcohol)).collect(Collectors.toList());
    return new GetAlcoholResponseDto(list, totalCount);
  }
}
