package com.bside.bside_311;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import com.bside.bside_311.util.AuthUtil;

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
