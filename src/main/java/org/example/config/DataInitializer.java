package org.example.config;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner test(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("liza")) {
                User user = new User();
                user.setUsername("liza");
                user.setPassword(passwordEncoder.encode("liza123"));
                user.setRole("USER");
                userRepository.save(user);
                System.out.println("Default user created: liza/liza123");
            } else {
                System.out.println("User already exists.");
            }
        };
    }
}
