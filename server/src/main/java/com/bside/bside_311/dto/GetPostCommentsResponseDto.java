package com.bside.bside_311.dto;

import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class GetPostCommentsResponseDto {
  private List<CommentResponseDto> list;
  private Long totalCount;

  public static GetPostCommentsResponseDto of(List<Comment> comments,
                                              Map<Long, User> createdByToUser,
                                              Map<Long, List<AttachDto>> uToAMap) {
    List<CommentResponseDto> list =
        comments.stream().map(comment -> CommentResponseDto.of(comment
            , createdByToUser.get(comment.getCreatedBy()),
            uToAMap.get(comment.getCreatedBy()))).collect(Collectors.toList());
    return new GetPostCommentsResponseDto(list, (long) list.size());
  }
}
