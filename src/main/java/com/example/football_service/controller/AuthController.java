package com.example.football_service.controller;

import com.example.football_service.domain.common.request.LoginRequest;
import com.example.football_service.domain.entities.AppUser;
import com.example.football_service.repository.UserRepository;
import com.example.football_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody LoginRequest user) {
        return userRepository.findByEmail(user.email()).getId().toString();
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody AppUser user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        AppUser newUser = new AppUser(user.getFirstName(),
                user.getLastName(),
                encoder.encode(user.getPassword()),
                user.getEmail()
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
