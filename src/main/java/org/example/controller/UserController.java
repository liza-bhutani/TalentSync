package org.example.controller;

import org.example.dto.AuthRequest;
import org.example.dto.AuthResponse;
import org.example.model.User;
import org.example.security.JWTService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Optional<User> user = userService.authenticate(request.getUsername(), request.getPassword());
        if (user.isPresent()) {
            String token = jwtService.generateToken(user.get().getUsername());
            return new AuthResponse(token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during registration");
        }
    }


    @GetMapping("/user/{username}")
    public Optional<User> getUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
