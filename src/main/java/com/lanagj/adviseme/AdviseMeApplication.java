package com.lanagj.adviseme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AdviseMeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdviseMeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// just in case
	}
}
