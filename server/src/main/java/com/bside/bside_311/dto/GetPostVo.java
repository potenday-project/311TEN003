package com.bside.bside_311.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetPostVo {
  Long page;
  Long size;
  Long offset;

  String orderColumn;
  String orderType;
  String searchKeyword;
  List<Long> searchUserNoList;

  public GetPostVo(Long page, Long size, Long offset, String orderColumn, String orderType,
                   String searchKeyword, List<Long> searchUserNoList) {
    this.page = page;
    this.size = size;
    this.offset = offset;
    this.orderColumn = orderColumn;
    this.orderType = orderType;
    this.searchKeyword = searchKeyword;
    this.searchUserNoList = searchUserNoList;
  }
}
