package com.app.BullyBeGone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition
public class BullyBeGoneApplication {

	@Bean
	public GroupedOpenApi api()
	{
		return GroupedOpenApi.builder()
				.group("OpenApiController")
				.packagesToScan("com.app.BullyBeGone.controller")
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BullyBeGoneApplication.class, args);
	}

}
