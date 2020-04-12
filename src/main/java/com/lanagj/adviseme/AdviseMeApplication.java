package com.lanagj.adviseme;

import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdviseMeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdviseMeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// just in case
	}
}
