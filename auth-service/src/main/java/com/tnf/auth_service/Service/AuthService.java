package com.tnf.auth_service.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tnf.auth_service.DTO.AuthRequest;
import com.tnf.auth_service.DTO.AuthResponse;
import com.tnf.auth_service.DTO.LoginResponse;
import com.tnf.auth_service.Entity.User;
import com.tnf.auth_service.Repository.UserRepository;
import com.tnf.auth_service.Util.JWTUtil;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(AuthRequest request) {
        logger.info("registering user: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        logger.info("User registered: {}", savedUser.getUsername());

        return savedUser;
    }

    public AuthResponse loginUser(LoginResponse request) {
        logger.info("Login attempt: {}", request.getUsernameOrEmail());

        Optional<User> userOpt = userRepository.findByUsername(request.getUsernameOrEmail());
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid username/email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username/email or password");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        long expiresIn = jwtUtil.extractExpiration(token).getTime() - System.currentTimeMillis();

        logger.info("Login successful: {}", user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRoles(), expiresIn);
    }

    public User getUserProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}