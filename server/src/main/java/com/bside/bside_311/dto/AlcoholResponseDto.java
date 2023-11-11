package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Alcohol;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class AlcoholResponseDto {

  @Schema(example = "1", description = "주류 번호.")
  private Long alcoholNo;

  @Schema(example = "1", description = "주류 종류 번호.(DB에 등록된것만 가능. - 와인, 맥주, 소주)")
  private Long alcoholTypeNo;

  @Schema(example = "와인", description = "주류 종류 이름.(DB에 등록된것만 가능. - 와인, 맥주, 소주)")
  private String alcoholType;

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

  public static AlcoholResponseDto of(Alcohol alcohol) {
    return AlcoholResponseDto.builder().alcoholNo(alcohol.getId())
                             .alcoholName(alcohol.getName())
                             .alcoholTypeNo(alcohol.getAlcoholType().getId())
                             .alcoholType(alcohol.getAlcoholType().getName())
                             .nickNames(alcohol.getAlcoholNicknames().stream().map(
                                 alcoholNickname -> alcoholNickname.getNickname()).toList())
                             .manufacturer(alcohol.getManufacturer())
                             .description(alcohol.getDescription())
                             .degree(alcohol.getDegree())
                             .period(alcohol.getPeriod())
                             .productionYear(alcohol.getProductionYear())
                             .volume(alcohol.getVolume())
                             .build();
  }
}
