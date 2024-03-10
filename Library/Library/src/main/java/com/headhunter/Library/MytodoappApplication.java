package com.headhunter.Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class MytodoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytodoappApplication.class, args);
	}

}
