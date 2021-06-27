package com.game.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.game.model")
@EnableJpaRepositories(basePackages = "com.game.dao")
public class BowlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingApplication.class, args);
	}

}
