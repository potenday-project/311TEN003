package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Attach;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UploadAttachResponseDto {
  @Schema(example = "1", description = "첨부 번호")
  private Long attachNo;

  public static UploadAttachResponseDto of(Attach attach) {
    return new UploadAttachResponseDto(attach.getId());
  }
}
