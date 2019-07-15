package br.com.Sparta.SpartaSalesManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@SpringBootApplication
public class SpartaSalesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpartaSalesManagerApplication.class, args);
	}
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension(){
		return new SecurityEvaluationContextExtension();
	}
}
