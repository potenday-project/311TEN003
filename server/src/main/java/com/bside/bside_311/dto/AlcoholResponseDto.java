package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Alcohol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlcoholResponseDto {

  @Schema(example = "1", description = "주류 번호.")
  private Long alcoholNo;

  @Schema(example = "톰슨 앳 스캇", description = "주류 이름.")
  private String alcoholName;

  private List<String> nickNames;

  @Schema(example = "오비맥주", description = "제조 업체")
  private String manufacturer;

  @Schema(example = "산미가 강한 와인.", description = "설명.")
  private String description;

  @Schema(example = "17.5", description = "알코올 도수")
  private Float degree;

  @Schema(example = "20", description = "숙성 기간(년단위)")
  private Long period;

  @Schema(example = "2019", description = "제조 연도")
  private Long productionYear;

  @Schema(example = "700", description = "용량(ml)")
  private Long volume;

  @Builder
  public AlcoholResponseDto(Long alcoholNo, String alcoholName, List<String> nickNames,
                            String manufacturer, String description, Float degree, Long period,
                            Long productionYear, Long volume) {
    this.alcoholNo = alcoholNo;
    this.alcoholName = alcoholName;
    this.nickNames = nickNames;
    this.manufacturer = manufacturer;
    this.description = description;
    this.degree = degree;
    this.period = period;
    this.productionYear = productionYear;
    this.volume = volume;
  }

  public static AlcoholResponseDto of(Alcohol alcohol) {
    return AlcoholResponseDto.builder().alcoholNo(alcohol.getId())
        .alcoholName(alcohol.getName())
        .nickNames(alcohol.getAlcoholNicknames().stream().map(alcoholNickname -> alcoholNickname.getNickname()).toList())
        .manufacturer(alcohol.getManufacturer())
        .description(alcohol.getDescription())
        .degree(alcohol.getDegree())
        .period(alcohol.getPeriod())
        .productionYear(alcohol.getProductionYear())
        .volume(alcohol.getVolume())
        .build();
  }
}
