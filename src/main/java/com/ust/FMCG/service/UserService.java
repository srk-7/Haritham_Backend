package com.ust.FMCG.service;

import com.ust.FMCG.dto.LoginRequest;
import com.ust.FMCG.dto.UserProfileUpdateRequest;
import com.ust.FMCG.model.Order;
import com.ust.FMCG.model.User;
import com.ust.FMCG.repository.OrderRepository;
import com.ust.FMCG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // SecureRandom for salt generation
    private static final SecureRandom secureRandom = new SecureRandom();

    // Hash password with SHA-256 + salt
    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public User registerUser(User user) {
        if (userRepository.existsByMobile(user.getMobile())) {
            throw new RuntimeException("User already registered with this mobile");
        }

        // Generate salt
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        // Hash password with salt
        String hashedPwd = hashPassword(user.getPassword(), salt);

        user.setPassword(hashedPwd);
        user.setSalt(saltBase64);

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = (User) userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Decode stored salt
        byte[] salt = Base64.getDecoder().decode(user.getSalt());
        String hashedInputPwd = hashPassword(request.getPassword(), salt);

        if (!hashedInputPwd.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public User updateProfile(UserProfileUpdateRequest updateRequest) {
        User user = userRepository.findById(updateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmpId(updateRequest.getEmpId());
        user.setName(updateRequest.getName());
        user.setMobile(updateRequest.getMobile());
        if (updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isBlank()) {
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashedPwd = hashPassword(updateRequest.getNewPassword(), salt);

            user.setPassword(hashedPwd);
            user.setSalt(saltBase64);
        }
        return userRepository.save(user);
    }

    public User findUserById(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.orElse(null);
    }

    public List<Order> getOrderHistory(String userId) {
        return orderRepository.findByBuyerId(userId);
    }
}
