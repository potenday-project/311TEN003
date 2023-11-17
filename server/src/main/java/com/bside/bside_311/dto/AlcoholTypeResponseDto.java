package com.bside.bside_311.dto;

import com.bside.bside_311.entity.AlcoholType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class AlcoholTypeResponseDto {
  private Long alcoholTypeNo;
  private String name;
  private String description;

  public static AlcoholTypeResponseDto of(AlcoholType alcoholType) {
    return new AlcoholTypeResponseDto(alcoholType.getId(), alcoholType.getName(),
        alcoholType.getDescription());
  }
}
