package com.lanagj.adviseme;

import com.lanagj.adviseme.data_import.tmdb.TmdbImportService;
import com.lanagj.adviseme.entity.user.Role;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
public class AdviseMeApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AdviseMeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		//this.userRepository.save(new User("admin", bCryptPasswordEncoder.encode("admin"), Role.ROLE_ADMIN));
	}
}
