package com.bside.bside_311.controller;

import com.bside.bside_311.dto.ImageRequestDto;
import com.bside.bside_311.dto.UploadAttachResponseDto;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.service.AttachService;
import com.bside.bside_311.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
@Tag(name = "첨부", description = "첨부 API")
public class AttachController {
  private final AttachService attachService;

  @Operation(summary = "[o]사진 첨부", description = "사진 첨부 API")
  @PostMapping(value = "/resources/{attachType}/{resourceNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UploadAttachResponseDto userAttachPicture(
      @PathVariable("resourceNo") @Schema(example = "1", description = "리소스의 PK") Long resourceNo,
      @PathVariable("attachType")
      @Schema(example = "POST", description = "리소스의 타입(POST, PROFILE, ALCOHOL 가능.)")
      String attachType,
      @ModelAttribute
      @Valid ImageRequestDto imageRequestDto)
      throws IOException {
    log.info(">>> PostController.uploadAttach");
    return UploadAttachResponseDto.of(
        attachService.uploadAttach(imageRequestDto, resourceNo, AttachType.valueOf(attachType)));
  }

  @Operation(summary = "[o]사진 삭제", description = "사진 삭제 API")
  @DeleteMapping("/{attachNo}")
  public void userDeletePicture(@PathVariable("attachNo") Long attachNo) {
    // FIXME 자신이 소유하고 있는 자원만 접근이 가능하도록 조치.
    log.info(">>> PostController.userDeletePicture");
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    attachService.deleteAttach(attachNo, myUserNo);
  }
}
