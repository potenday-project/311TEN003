package com.bside.bside_311.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GetPostResponseDto {
	private List<PostResponseDto> list;
	private Long totalCount;

	public static GetPostResponseDto of(List<PostResponseDto> list, Long totalCount) {
		return new GetPostResponseDto(list, totalCount);
	}
}
