package com.bside.bside_311.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FindUserMvo {
	private String userNo;
	private String email;
	private LocalDateTime createdDate;
}
