package com.lanagj.adviseme;

import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AdviseMeApplication implements CommandLineRunner {

	@Autowired
	TmdbImportService tmdbImportService;

	public static void main(String[] args) {
		SpringApplication.run(AdviseMeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//this.tmdbImportService.importMovies(3, 2000, 2017);
	}
}
