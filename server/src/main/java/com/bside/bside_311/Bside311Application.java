package com.bside.bside_311;

import com.bside.bside_311.util.AuthUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@SpringBootApplication
public class Bside311Application {

  public static void main(String[] args) {
    SpringApplication.run(Bside311Application.class, args);
  }

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return () -> {
      Long userNoFromAuthentication = AuthUtil.getUserNoFromAuthentication();
      if (userNoFromAuthentication == null) {
        return Optional.empty();
      }
      return Optional.of(userNoFromAuthentication);
    };
  }

}
