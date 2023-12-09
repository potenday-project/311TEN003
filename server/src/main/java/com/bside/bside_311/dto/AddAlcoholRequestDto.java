package com.bside.bside_311.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class AddAlcoholRequestDto {
  @Schema(example = "톰슨 앳 스캇", description = "주류 이름.(중복 허용X)")
  @NotBlank
  private String alcoholName;

  @Schema(example = "1", description = "주류 종류 번호.(DB에 등록된것만 가능. - 와인, 맥주, 소주)")
  @Positive
  @NotNull
  private Long alcoholTypeNo;

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
  private Long period;

  @Schema(example = "2019", description = "제조 연도")
  @Digits(integer = 4, fraction = 0)
  private Long productionYear;

  @Schema(example = "700", description = "용량(ml)")
  @Positive
  private Long volume;

  @Schema(example = " {\n" +
                        "      \"Aroma\": \"과일, 다채로운\",\n" +
                        "      \"Taste\": \"과일, 오크, 초콜릿, 산미\",\n" +
                        "      \"Finish\": \"우아한\"\n" +
                        "    }")
  private Map<String, String> taste;

  @Builder.Default
  @Schema(description = "태그 리스트. 해당 문자열 없을경우 데이터 생성.")
  private List<String> tagList = new ArrayList<>();

}
