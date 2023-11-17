package com.bside.bside_311.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetAlcoholsMvo {
  Long alcoholNo;
  String name;
  List<String> nicknames;
  String manufacturer;
  String description;
  Float degree;
  Long period;
  Long productionYear;
  Long volume;

  Long createdBy;
  LocalDateTime createdDate;
  Long lastModifiedBy;
  LocalDateTime lastModifiedDate;
}
