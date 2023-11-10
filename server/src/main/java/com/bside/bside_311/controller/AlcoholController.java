package com.bside.bside_311.controller;

import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.AddAlcoholResponseDto;
import com.bside.bside_311.dto.AlcoholResponseDto;
import com.bside.bside_311.dto.EditAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholResponseDto;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.service.AlcoholService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
@Tag(name = "술", description = "술 API")
public class AlcoholController {
  private final AlcoholService alcoholService;

  @Operation(summary = "[o]술 등록 ", description = "술 등록 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AddAlcoholResponseDto addAlcohol(@RequestBody @Valid AddAlcoholRequestDto addAlcoholRequestDto){
    log.info(">>> AlcoholController.addAlcohol");
    return alcoholService.addAlcohol(Alcohol.of(addAlcoholRequestDto));
  }

  @Operation(summary = "[o]술 수정", description = "술 수정 API")
  @PatchMapping("/{alcoholNo}")
  public void editAlcohol(@PathVariable("alcoholNo") Long alcoholNo, @RequestBody @Valid EditAlcoholRequestDto editAlcoholRequestDto) {
    log.info(">>> AlcoholController.editAlcohol");
    alcoholService.editAlcohol(alcoholNo, editAlcoholRequestDto);
  }

  @Operation(summary = "[o]술 삭제", description = "술 삭제 API")
  @DeleteMapping("/{alcoholNo}")
  public void deleteAlcohol(@PathVariable("alcoholNo") Long alcoholNo) {
    log.info(">>> AlcoholController.deleteAlcohol");
    alcoholService.deleteAlcohol(alcoholNo);
  }

  @Operation(summary = "[~]술 목록 조회", description = "술 조회 API")
  @GetMapping
  public GetAlcoholResponseDto getAlcohol(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) String orderColumn,
      @RequestParam(required = false) String orderType,
      @RequestParam(required = false) String searchAlcoholKeyword) {
    log.info(">>> AlcoholController.getAlcohol");
    return alcoholService.getAlcohol(page, size, orderColumn, orderType, searchAlcoholKeyword);
  }

  @Operation(summary = "[o]술 상세 조회", description = "술 상세 조회 API")
  @GetMapping("/{alcoholNo}")
  public AlcoholResponseDto getAlcoholDetail(@PathVariable("alcoholNo") Long alcoholNo) {
    log.info(">>> AlcoholController.getAlcoholDetail");

    return alcoholService.getAlcoholDetail(alcoholNo);
  }

}
