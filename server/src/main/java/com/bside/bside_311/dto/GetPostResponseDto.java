package com.bside.bside_311.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetPostResponseDto {
  private List<PostResponseDto> list;
  private int totalCount;
}
