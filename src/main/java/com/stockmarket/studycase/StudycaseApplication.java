package com.stockmarket.studycase;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title="Stock Market API",
				description = "Stock Market OPEN API Documentation",
				version = "3.2.2"),
		servers = @Server(
				url = "http://localhost:8080",
				description = "Stock Market Api Url"
		)
)
public class StudycaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudycaseApplication.class, args);
	}

}
