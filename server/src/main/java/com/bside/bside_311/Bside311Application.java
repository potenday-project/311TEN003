package com.bside.bside_311;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class Bside311Application {

	public static void main(String[] args) {
		SpringApplication.run(Bside311Application.class, args);
	}

	@Bean
	public AuditorAware<Integer> auditorProvider() {
		return () -> Optional.of(1);
	}

}
