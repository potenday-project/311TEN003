package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.util.JsonParseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class AlcoholResponseDto {

  @Schema(example = "1", description = "주류 번호.")
  private Long alcoholNo;

  @Schema(example = "1", description = "주류 종류 번호.(DB에 등록된것만 가능. - 와인, 맥주, 소주)")
  private Long alcoholTypeNo;

  @Schema(description = "주류 첨부 링크 객체 리스트.")
  @Builder.Default
  private List<AttachDto> alcoholAttachUrls = new ArrayList<>();

  @Schema(example = "와인", description = "주류 종류 이름.(DB에 등록된것만 가능. - 와인, 맥주, 소주)")
  private String alcoholType;

  @Schema(example = "톰슨 앳 스캇", description = "주류 이름.")
  private String alcoholName;


  @Builder.Default
  private List<String> nickNames = new ArrayList<>();

  @Schema(example = "오비맥주", description = "제조 업체")
  private String manufacturer;

  @Schema(example = "산미가 강한 와인.", description = "설명.")
  private String description;

  @Schema(example = "json문자열.", description = "tastingNote(맛, 기타 등등.)")
  private Map<String, Object> taste;

  @Schema(example = "17.5", description = "알코올 도수")
  private Float degree;

  @Schema(example = "20", description = "숙성 기간(년단위)")
  private Long period;

  @Schema(example = "2019", description = "제조 연도")
  private Long productionYear;

  @Schema(example = "700", description = "용량(ml)")
  private Long volume;

  @Builder.Default
  private List<String> tagList = new ArrayList<>();

  public static AlcoholResponseDto of(Alcohol alcohol, List<AttachDto> attachDtos, List<Tag> tags) {
    AlcoholResponseDtoBuilder alcoholResponseDtoBuilder =
        AlcoholResponseDto.builder().alcoholNo(alcohol.getId())
                          .alcoholName(alcohol.getName())
                          .alcoholTypeNo(
                              alcohol.getAlcoholType().getId())
                          .alcoholType(
                              alcohol.getAlcoholType().getName())
                          .nickNames(
                              alcohol.getAlcoholNicknames().stream()
                                     .map(
                                         alcoholNickname -> alcoholNickname.getNickname())
                                     .toList())
                          .manufacturer(alcohol.getManufacturer())
                          .description(alcohol.getDescription())
                          .taste(JsonParseUtil.tasteMapProcessing(alcohol.getTaste()))
                          .degree(alcohol.getDegree())
                          .period(alcohol.getPeriod())
                          .productionYear(
                              alcohol.getProductionYear())
                          .volume(alcohol.getVolume());
    if (CollectionUtils.isNotEmpty(attachDtos)) {
      alcoholResponseDtoBuilder.alcoholAttachUrls(attachDtos);
    }
    if (CollectionUtils.isNotEmpty(tags)) {
      alcoholResponseDtoBuilder.tagList(
          tags.stream().map(Tag::getName).toList());
    }
    return alcoholResponseDtoBuilder
               .build();
  }

  public static AlcoholResponseDto of(Alcohol alcohol, List<AttachDto> attachDtos) {
    List<AlcoholTag> alcoholTags = alcohol.getAlcoholTags().stream()
                                          .filter(postTag -> YesOrNo.N == postTag.getDelYn())
                                          .collect(Collectors.toList());
    List<String> tagList = alcoholTags.stream()
                                      .filter(
                                          alcoholTag -> YesOrNo.N == alcoholTag.getTag().getDelYn())
                                      .map(alcoholTag -> alcoholTag.getTag().getName()).toList();

    return AlcoholResponseDto.builder().alcoholNo(alcohol.getId())
                             .alcoholName(alcohol.getName())
                             .alcoholTypeNo(
                                 alcohol.getAlcoholType().getId())
                             .alcoholType(
                                 alcohol.getAlcoholType().getName())
                             .nickNames(
                                 alcohol.getAlcoholNicknames().stream()
                                        .filter(alcoholNickname -> alcoholNickname.getDelYn() ==
                                                                       YesOrNo.N).map(
                                            alcoholNickname -> alcoholNickname.getNickname())
                                        .toList())
                             .manufacturer(alcohol.getManufacturer())
                             .description(alcohol.getDescription())
                             .taste(JsonParseUtil.tasteMapProcessing(alcohol.getTaste()))
                             .degree(alcohol.getDegree())
                             .period(alcohol.getPeriod())
                             .productionYear(
                                 alcohol.getProductionYear())
                             .volume(alcohol.getVolume())
                             .alcoholAttachUrls(attachDtos)
                             .tagList(tagList).build();
  }
}

