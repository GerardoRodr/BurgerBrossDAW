package com.cibertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BurgerBrossApplication {

	public static void main(String[] args) {
		SpringApplication.run(BurgerBrossApplication.class, args);
	}

}