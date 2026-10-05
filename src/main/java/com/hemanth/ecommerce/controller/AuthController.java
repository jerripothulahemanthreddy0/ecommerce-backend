package com.hemanth.ecommerce.controller;

import com.hemanth.ecommerce.entity.User;
import com.hemanth.ecommerce.repository.UserRepository;
import com.hemanth.ecommerce.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User existingUser = userRepository
                .findAll()
                .stream()
                .filter(u -> u.getEmail().equals(user.getEmail())
                        && u.getPassword().equals(user.getPassword()))
                .findFirst()
                .orElse(null);

        if (existingUser == null) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password");
        }

        String token = jwtService.generateToken(existingUser.getEmail());

        return ResponseEntity.ok(token);
    }
}