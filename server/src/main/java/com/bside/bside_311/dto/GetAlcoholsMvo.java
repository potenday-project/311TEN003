package com.bside.bside_311.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetAlcoholsMvo {
  private String userNo;
  private String email;
  private LocalDateTime createdDate;
}
