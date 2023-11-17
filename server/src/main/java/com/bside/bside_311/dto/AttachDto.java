package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Attach;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class AttachDto {
  @Schema(example = "1", description = "첨부 번호")
  private Long attachNo;
  @Schema(example = "http://www.naver.com", description = "프로필 사진 링크.")
  private String attachUrl;
  @Schema(example = "profile", description = "첨부 타입")
  private String attachType;

  public static List<AttachDto> of(List<Attach> attaches) {
    return attaches.stream().map(AttachDto::of)
                   .toList();
  }

  public static AttachDto of(Attach attach) {
    return AttachDto.builder()
                    .attachNo(attach.getId())
                    .attachUrl(attach.getAttachUrl())
                    .attachType(attach.getAttachType().name())
                    .build();
  }
}
