package org.example;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WorkSphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkSphereApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("liza").isEmpty()) {
                User user = new User();
                user.setUsername("liza");
                user.setPassword(new BCryptPasswordEncoder().encode("liza123"));
                user.setRole("USER");
                userRepository.save(user);
                System.out.println("Default user created: liza / liza123");
            } else {
                System.out.println("User 'liza' already exists.");
            }
        };
    }
}
