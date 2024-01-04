package com.abdul.springbootjwttokenauth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Authentication Api", version = "1.0", description = "Auth API"))
public class SpringbootJwtTokenAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJwtTokenAuthApplication.class, args);
	}

}
