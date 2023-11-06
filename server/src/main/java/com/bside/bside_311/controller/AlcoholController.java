package com.bside.bside_311.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/alcohols")
@Tag(name = "술", description = "술 API")
public class AlcoholController {

  @Operation(summary = "술 등록 ", description = "술 등록 API")
  @PostMapping
  public void addAlcohol() {
    log.info(">>> AlcoholController.addAlcohol");
  }

  @Operation(summary = "술 수정", description = "술 수정 API")
  @PatchMapping
  public void editAlcohol() {
    log.info(">>> AlcoholController.editAlcohol");
  }

  @Operation(summary = "술 삭제", description = "술 삭제 API")
  @DeleteMapping
  public void deleteAlcohol() {
    log.info(">>> AlcoholController.deleteAlcohol");
  }

  @Operation(summary = "술 목록 조회", description = "술 조회 API")
  @GetMapping
  public void getAlcohol() {
    log.info(">>> AlcoholController.getAlcohol");
  }

  @Operation(summary = "술 상세 조회", description = "술 상세 조회 API")
  @GetMapping("/{alcohol_no}")
  public void getAlcoholDetail() {
    log.info(">>> AlcoholController.getAlcoholDetail");
  }

}
