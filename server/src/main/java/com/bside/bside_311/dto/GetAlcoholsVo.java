package com.bside.bside_311.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAlcoholsVo {
  Long page;
  Long size;
  Long offset;

  String orderColumn;
  String orderType;
  String searchKeyword;

  @Builder
  public GetAlcoholsVo(Long page, Long size, Long offset, String orderColumn,
                       String orderType, String searchKeyword) {
    this.page = page;
    this.size = size;
    this.offset = offset;
    this.orderColumn = orderColumn;
    this.orderType = orderType;
    this.searchKeyword = searchKeyword;
  }
}
