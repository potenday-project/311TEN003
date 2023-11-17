package com.bside.bside_311.dto;

import com.bside.bside_311.entity.AlcoholType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class GetAlcoholTypesResponseDto {
  private List<AlcoholTypeResponseDto> list;
  private Long totalCount;

  public static GetAlcoholTypesResponseDto of(List<AlcoholType> alcoholTypes) {
    List<AlcoholTypeResponseDto> list =
        alcoholTypes.stream().map(AlcoholTypeResponseDto::of)
                    .collect(java.util.stream.Collectors.toList());
    return new GetAlcoholTypesResponseDto(list, (long) list.size());
  }
}
