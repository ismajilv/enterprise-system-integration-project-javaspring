package com.buildit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@CrossOrigin()
public class BuilditApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuilditApplication.class, args);

		addTestData();
	}
	private static void addTestData() {
	}

}
