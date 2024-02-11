package com.prueba.tecnica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/*
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html
*/

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Heroes API")
						.description("API REST para superhéroes")
						.version("v1.0.0"));
	}
}
