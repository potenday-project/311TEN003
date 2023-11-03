package com.bside.bside_311.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {
  private final TestRepository testRepository;
}
