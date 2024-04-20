package com.bside.bside_311.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetPostsMvo {
	Long postNo;
	String content;
	String type;
	String position;

	Long createdBy;
	LocalDateTime createdDate;
	Long lastModifiedBy;
	LocalDateTime lastModifiedDate;
}
