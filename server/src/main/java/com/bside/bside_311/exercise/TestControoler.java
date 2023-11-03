package com.bside.bside_311.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestControoler {
  private final TestService testService;

  @GetMapping
  public String test() {
    return "test";
  }
}
