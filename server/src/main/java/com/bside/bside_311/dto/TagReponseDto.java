package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

@AllArgsConstructor
public class TagReponseDto {
  private String name;
  private LocalDateTime createdDate;

  public static TagReponseDto of(Tag tag) {
    return new TagReponseDto(tag.getName(), tag.getCreatedDate());
  }

}
