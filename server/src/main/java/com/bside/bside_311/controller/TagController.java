package com.bside.bside_311.controller;

import com.bside.bside_311.dto.SearchTagResponseDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@Tag(name = "태그", description = "태그 API")
public class TagController {
  private final TagService tagService;

  @Operation(summary = "[o]태그 조회", description = "태그 조회 API")
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public SearchTagResponseDto searchTag(
                    @RequestParam(name="page", defaultValue ="0")
                    @Schema(description = "검색 키워드 기본값 빈값", example = "")
                    String searchKeyword)
  {
    return SearchTagResponseDto.of(tagService.searchTag(searchKeyword));
  }
}
