package UserApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Projet TP API",
				version = "1.0",
				description = "API Documentation for Spring Boot Projet TP"
		)
)
public class ProjetTpApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetTpApplication.class, args);
	}
}
