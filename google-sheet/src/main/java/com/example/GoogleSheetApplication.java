package com.example;

import com.example.repository.UserRepository;
import com.example.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class GoogleSheetApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(GoogleSheetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Check if the default user already exists
		if (userRepository.findByEmail("superadmin@example.com").isEmpty()) {
			// Create a new default user
			User defaultUser = new User();
			defaultUser.setFirstName("super");
			defaultUser.setLastName("admin");
			defaultUser.setEmail("superadmin@example.com");
			defaultUser.setDivision("super_admin");
			defaultUser.setPassword(passwordEncoder.encode("admin"));  // Encode password
			defaultUser.setRole(User.Role.SUPER_ADMIN);

			// Save the default user
			userRepository.save(defaultUser);

			System.out.println("Default user created: superadmin@example.com");
		} else {
			System.out.println("Admin user already exists.");
		}
	}
}
