package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddAlcoholResponseDto {
  @Schema(example = "1", description = "술 번호")
  private Long alcoholNo;

  private AddAlcoholResponseDto(Long alcoholNo) {
    this.alcoholNo = alcoholNo;
  }

  public static AddAlcoholResponseDto of(Long alcoholNo) {
    return new AddAlcoholResponseDto(alcoholNo);
  }
}
