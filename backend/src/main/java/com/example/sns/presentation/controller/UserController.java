package com.example.sns.presentation.controller;

import com.example.sns.application.service.UserService;
import com.example.sns.domain.model.user.User;
import com.example.sns.presentation.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.register(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromDomain(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable String userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        User user = userService.updateProfile(userId, request.getDisplayName(), request.getBio());
        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }
}
