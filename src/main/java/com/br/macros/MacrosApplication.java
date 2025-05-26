package com.br.macros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MacrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacrosApplication.class, args);
	}

}
