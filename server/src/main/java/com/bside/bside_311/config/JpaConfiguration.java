package com.bside.bside_311.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Profile("!test")
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
}
