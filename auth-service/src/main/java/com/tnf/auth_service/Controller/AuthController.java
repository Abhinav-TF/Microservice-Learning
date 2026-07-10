package com.tnf.auth_service.Controller;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.auth_service.DTO.AuthRequest;
import com.tnf.auth_service.DTO.AuthResponse;
import com.tnf.auth_service.DTO.LoginResponse;
import com.tnf.auth_service.Entity.User;
import com.tnf.auth_service.Service.AuthService;
import com.tnf.auth_service.Util.JWTUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerEntity(@Valid @RequestBody AuthRequest request) {
        logger.info("Register request for: {}", request.getUsername());
        User user = authService.registerUser(request);
        Map<String , Object> response  = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("roles", user.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginResponse request) {
        logger.info("Login request for: {}", request.getUsernameOrEmail());
        AuthResponse authResponse = authService.loginUser(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        logger.info("Token validation request");
        Map<String, Object> response = new HashMap<>();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("message", "Invalid Authorization header");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String token = authHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            response.put("valid", true);
            response.put("username", username);
            response.put("roles", jwtUtil.extractRoles(token));
            return ResponseEntity.ok(response);
        } else {
            response.put("valid", false);
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUserEntity(@RequestHeader("X-Auth-Username") String username) {
        logger.info("Getting profile for user: {}", username);
        User user = authService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAllUser(@RequestHeader("X-Auth-Roles") String rolesHeader) {
        logger.info("Getting all users");
        if(rolesHeader == null || !rolesHeader.contains("ADMIN")) {
            throw new RuntimeException("Access denied: Admin role required");
        }

        Iterable<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        logger.info("logout request");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }
    
    

}
