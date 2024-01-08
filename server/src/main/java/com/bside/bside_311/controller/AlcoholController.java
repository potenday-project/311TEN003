package com.bside.bside_311.controller;

import com.bside.bside_311.config.security.AdminRequired;
import com.bside.bside_311.config.security.UserRequired;
import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.AddAlcoholResponseDto;
import com.bside.bside_311.dto.AlcoholResponseDto;
import com.bside.bside_311.dto.EditAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholResponseDto;
import com.bside.bside_311.dto.GetAlcoholTypesResponseDto;
import com.bside.bside_311.service.AlcoholService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
@Tag(name = "술", description = "술 API")
public class AlcoholController {
  private final AlcoholService alcoholService;

  @Operation(summary = "[o]술 종류 조회 ", description = "술 종류 조회 API")
  @GetMapping("/types")
  @ResponseStatus(HttpStatus.OK)
  public GetAlcoholTypesResponseDto getAlcoholTypes() {
    log.info(">>> AlcoholController.getAlcoholTypes");
    return alcoholService.getAlcoholTypes();
  }

  @Operation(summary = "[o]술 등록 ", description = "술 등록 API")
  @PostMapping
  @UserRequired
  @ResponseStatus(HttpStatus.CREATED)
  public AddAlcoholResponseDto addAlcohol(
      @RequestBody @Valid AddAlcoholRequestDto addAlcoholRequestDto)
      throws JsonProcessingException {
    log.info(">>> AlcoholController.addAlcohol");
    return alcoholService.addAlcohol(addAlcoholRequestDto);
  }

  @Operation(summary = "[o]술 수정", description = "술 수정 API")
  @PatchMapping("/{alcoholNo}")
  @AdminRequired
  public void editAlcohol(@PathVariable("alcoholNo") Long alcoholNo,
                          @RequestBody @Valid EditAlcoholRequestDto editAlcoholRequestDto) {
    log.info(">>> AlcoholController.editAlcohol");
    alcoholService.editAlcohol(alcoholNo, editAlcoholRequestDto);
  }

  @Operation(summary = "[o]술 삭제", description = "술 삭제 API")
  @DeleteMapping("/{alcoholNo}")
  @AdminRequired
  public void deleteAlcohol(@PathVariable("alcoholNo") Long alcoholNo) {
    log.info(">>> AlcoholController.deleteAlcohol");
    alcoholService.deleteAlcohol(alcoholNo);
  }

  @Operation(summary = "[o]술 목록 조회", description = "술 조회 API")
  @GetMapping
  public GetAlcoholResponseDto getAlcohol(
      @RequestParam(name = "page", defaultValue = "0")
      @Schema(description = "페이지번호(0부터), 기본값 0.", example = "0") Long page,
      @RequestParam(name = "size", defaultValue = "10")
      @Schema(description = "사이즈, 기본값 10.", example = "10") Long size,
      @RequestParam(required = false, name = "orderColumn")
      @Schema(description = "정렬 컬럼", example = "alcohol_no") String orderColumn,
      @RequestParam(required = false, name = "orderType")
      @Schema(description = "정렬 타입", example = "DESC") String orderType,
      @RequestParam(required = false, name = "searchKeyword")
      @Schema(description = "알코올 키워드", example = "키워드") String searchKeyword) {
    log.info(">>> AlcoholController.getAlcohol");
    return alcoholService.getAlcohol(page, size, orderColumn, orderType, searchKeyword);
  }

  @Operation(summary = "[o]술 목록 조회v2(성능 최적화)", description = "술 조회 API Page, size 사용법. <br> ex1) /alcohols/v2?page=0&size=10&sort=id,desc <br> ex2) /alcohols/v2?page=0&size=10&sort=id,desc&sort=content,asc&searchKeyword=키워드&searchUserNos=1,2,4")
  @GetMapping("/v2")
  public Page<AlcoholResponseDto> getAlcoholV2(
//      @Schema(description = "페이지 번호와 사이즈.정렬 까지.(0부터) ex)[1]page=0&size=5&sort=id,desc [2]page=1&size=15&sort=id,desc&sort=content,asc", example = "0")
      Pageable pageable,
      @RequestParam(required = false, name = "searchKeyword")
      @Schema(description = "키워드", example = "키워드")
      String searchKeyword,
      @RequestParam(required = false, name = "alcoholType")
      @Schema(description = "술 타입(선택)", example = "1")
      @Positive(message = "술 타입은 1이상의 숫자만 가능합니다.")
      Long alcoholType) {
    log.info(">>> AlcoholController.getAlcohol");
    return alcoholService.getAlcoholV2(pageable, searchKeyword, alcoholType);
  }

  @Operation(summary = "[o]술 상세 조회", description = "술 상세 조회 API")
  @GetMapping("/{alcoholNo}")
  public AlcoholResponseDto getAlcoholDetail(@PathVariable("alcoholNo") Long alcoholNo) {
    log.info(">>> AlcoholController.getAlcoholDetail");
    return alcoholService.getAlcoholDetail(alcoholNo);
  }

  @Operation(summary = "술타입 MAP 조회", description = "술타입 MAP 조회 API")
  @GetMapping("/types/map")
  public Map<String, Long> getAlcoholTypeMap() {
    log.info(">>> AlcoholController.getAlcoholTypeMap");
    return alcoholService.getAlcoholTypeMap();
  }

}
