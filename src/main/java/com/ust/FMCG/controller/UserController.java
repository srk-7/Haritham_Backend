package com.ust.FMCG.controller;

import com.ust.FMCG.dto.LoginRequest;
import com.ust.FMCG.dto.PasswordUpdateRequest;
import com.ust.FMCG.dto.UserProfileUpdateRequest;
import com.ust.FMCG.model.Order;
import com.ust.FMCG.model.User;
import com.ust.FMCG.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(userService.login(loginRequest));
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            return ResponseEntity.ok(userService.updateProfile(updateRequest));
        } catch (Exception e) {
            throw new RuntimeException("Profile update failed: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getOrderHistory(userId));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable String userId,
            @RequestBody PasswordUpdateRequest request) {
        try {
            return ResponseEntity.ok(userService.updatePassword(userId, request));
        } catch (Exception e) {
            throw new RuntimeException("Password update failed: " + e.getMessage());
        }
    }
}
