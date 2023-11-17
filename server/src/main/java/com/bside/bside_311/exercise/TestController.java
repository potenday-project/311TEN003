package com.bside.bside_311.exercise;

import com.bside.bside_311.config.security.AdminRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
  private final TestService testService;

  @GetMapping
  public String test() {
    return "test success";
  }

  @GetMapping("/admin/required")
  @AdminRequired
  public String testAdminRequired() {
//    return testService.testAdminRequired();
    return "test /admin/required success";
  }
}
