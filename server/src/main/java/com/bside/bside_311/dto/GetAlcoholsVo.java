package com.bside.bside_311.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAlcoholsVo {
  Integer page;
  Integer size;
  String orderColumn;
  String orderType;
  String searchAlcoholKeyword;

  @Builder
  public GetAlcoholsVo(Integer page, Integer size, String orderColumn, String orderType,
                       String searchAlcoholKeyword) {
    this.page = page;
    this.size = size;
    this.orderColumn = orderColumn;
    this.orderType = orderType;
    this.searchAlcoholKeyword = searchAlcoholKeyword;
  }
}
