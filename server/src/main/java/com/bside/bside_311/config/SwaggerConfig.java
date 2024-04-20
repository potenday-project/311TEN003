package com.bside.bside_311.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SwaggerConfig {

	@Value("${spring.application.name:-}")
	private String serviceId;

	@Bean
	public GroupedOpenApi testApi() {
		return GroupedOpenApi.builder()
			.group("TEST-API")
			.packagesToScan("com.bside.bside_311.exercise")
			.build();
	}

	@Bean(name = "nonTestApiBean")
	public GroupedOpenApi nonTestApi() {
		return GroupedOpenApi.builder()
			.group("API")
			.packagesToScan("com.bside.bside_311.controller")
			//                .packagesToExclude("kr.co.ezpmp.core.controller")
			.build();
	}

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		log.info(">>> bside311 Swagger Setting Service Name = {}, ", serviceId);
		Info info = new Info().title("").version(appVersion)
			.description("")
			.termsOfService("http://swagger.io/terms/")
			//                .contact(new Contact().name("bside").url("http://www.bside.co.kr/"))
			.license(new License().name("Apache License Version 2.0")
				.url("http://www.apache.org/licenses/LICENSE-2.0"));

		return new OpenAPI()
			.info(info)
			.components(new Components()
				.addSecuritySchemes("token", new SecurityScheme()
					.type(SecurityScheme.Type.APIKEY)
					.in(SecurityScheme.In.HEADER)
					.name("Authorization")))
			.addSecurityItem(new SecurityRequirement().addList("token"));
	}

}
