package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlcoholResponseDto {

  @Schema(example = "1", description = "주류 번호.")
  private Integer alcoholNo;
  @Schema(example = "톰슨 앳 스캇", description = "주류 이름.")
  private String alcoholName;
  @Schema(example = "톰슨 앳 스캇은 와인의 일종입니다.", description = "주류 설명")
  private String description;
  @Schema(example = "[\"톰슨\", \"스캇\"]", description = "주류 별칭 리스트")
  private List<String> nicknames;
  @Schema(example = "17.5", description = "알코올 도수")
  private float degreeOfAlcohol;
  @Schema(example = "20", description = "숙성 기간(년단위)")
  private Integer periodOfRipening;
  @Schema(example = "2019", description = "제조 연도")
  private Integer yearOfProduction;
  @Schema(example = "700", description = "용량(ml)")
  private Integer volume;
}
