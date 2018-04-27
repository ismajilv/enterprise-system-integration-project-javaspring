package com.buildit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuilditApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuilditApplication.class, args);

		addTestData();
	}

	private static void addTestData() {
	}

}
