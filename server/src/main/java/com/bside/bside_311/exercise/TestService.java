package com.bside.bside_311.exercise;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestService {
	private final TestRepository testRepository;
}
