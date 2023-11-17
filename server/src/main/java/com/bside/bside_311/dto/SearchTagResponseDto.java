package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class SearchTagResponseDto {
  private List<TagReponseDto> list;
  private Long totalCount;

  public static SearchTagResponseDto of(List<Tag> tags) {
    List<TagReponseDto> list =
        tags.stream().map(TagReponseDto::of)
                    .collect(java.util.stream.Collectors.toList());
    return new SearchTagResponseDto(list, (long) list.size());
  }

}
