package com.bside.bside_311.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UploadImageResultDto {
  private String attachUrl;
  private String publicId;

  public static UploadImageResultDto of(String secureUrl, String publicId) {
    return new UploadImageResultDto(secureUrl, publicId);
  }
}
