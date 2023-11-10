package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddAlcoholRequestDto {
  @Schema(example = "톰슨 앳 스캇", description = "주류 이름.(중복 허용X)")
  @NotBlank
  private String alcoholName;

  private List<String> nickNames;

  @Schema(example = "오비맥주", description = "제조 업체")
  private String manufacturer;

  @Schema(example = "산미가 강한 와인.", description = "설명.")
  private String description;

  @Schema(example = "17.5", description = "알코올 도수")
  @PositiveOrZero
  private Float degree;

  @Schema(example = "20", description = "숙성 기간(년단위)")
  @PositiveOrZero
  private Integer period;

  @Schema(example = "2019", description = "제조 연도")
  @Digits(integer = 4, fraction = 0)
  private Integer productionYear;

  @Schema(example = "700", description = "용량(ml)")
  @Positive
  private Integer volume;


  @Builder
  public AddAlcoholRequestDto(String alcoholName, List<String> nickNames, String manufacturer,
                              String description, Float degree, Integer period,
                              Integer productionYear, Integer volume) {
    this.alcoholName = alcoholName;
    this.nickNames = nickNames;
    this.manufacturer = manufacturer;
    this.description = description;
    this.degree = degree;
    this.period = period;
    this.productionYear = productionYear;
    this.volume = volume;
  }
}
