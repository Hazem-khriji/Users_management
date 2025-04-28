package UserApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@SpringBootApplication
public class ProjetTpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetTpApplication.class, args);
	}
	@Bean
	public Docket swaggerConfiguration() {
		return (new Docket(DocumentationType.SWAGGER_2)).select().paths(PathSelectors.ant("/api/employees/**")).apis(RequestHandlerSelectors.basePackage("gl2.example.personnel")).build().apiInfo(this.apiInfos());
	}
	private ApiInfo apiInfos() {
		return (new ApiInfoBuilder()).title("Employees API Application").description("Employees Documentation").version("1.0").termsOfServiceUrl("Employees Service Terms").contact(new Contact("Si Hazem", "http://google.com", "hazemkhriji0@gmail.com")).license("Si Hazem License").licenseUrl("http://google.com").extensions(Collections.emptyList()).build();
	}

}
