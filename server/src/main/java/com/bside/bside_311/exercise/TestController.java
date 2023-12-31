package com.bside.bside_311.exercise;

import com.bside.bside_311.config.security.AdminRequired;
import com.bside.bside_311.dto.UserResponseDto;
import com.bside.bside_311.dto.common.ResultDto;
import com.bside.bside_311.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

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

  @GetMapping("/resource")
  public ResponseEntity<Resource> getImage() {
    InputStream resourceAsStream =
        TestController.class.getResourceAsStream("/example/attach/post/1.png");
    if (resourceAsStream != null) {
      InputStreamResource resource = new InputStreamResource(resourceAsStream);
      return ResponseEntity.ok()
                           .contentType(MediaType.IMAGE_JPEG)
                           .body(resource);
    }
    return ResponseEntity.notFound()
                         .build();
  }

  @GetMapping("/common-result-dto")
  public ResultDto<UserResponseDto> testCommonResultDto() {
    return ResultDto.of("200", "success", UserResponseDto.of(User.of(1L)));
  }
}
