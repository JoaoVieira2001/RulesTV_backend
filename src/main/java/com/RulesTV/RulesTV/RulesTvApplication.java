package com.RulesTV.RulesTV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.RulesTV.RulesTV.repositories")
@EntityScan("com.RulesTV.RulesTV.entity")
public class RulesTvApplication {

	public static void main(String[] args) {

		SpringApplication.run(RulesTvApplication.class, args);
	}
}


