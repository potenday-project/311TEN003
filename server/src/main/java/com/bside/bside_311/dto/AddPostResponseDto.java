package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddPostResponseDto {
  @Schema(example = "1", description = "게시글 번호")
  private Long postNo;

  public AddPostResponseDto(Long postNo) {
    this.postNo = postNo;
  }

  public static AddPostResponseDto of(Post post) {
    return new AddPostResponseDto(post.getId());
  }
}
