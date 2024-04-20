package com.bside.bside_311.config.p6spy;

import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

import jakarta.annotation.PostConstruct;

/**
 * query multi line custom configure
 */
@Configuration
public class P6spyLogMessageFormatConfiguration {
	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6spySqlFormat.class.getName());
	}
}
