package com.zerotouch.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zerotouch.components"})
@EnableJpaRepositories(basePackages = {"com.zerotouch.JpaRepositories"})
@EntityScan(basePackages = {"com.zerotouch.EntityScan"})
public class ZeroTouchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeroTouchApplication.class, args);
	}

}
