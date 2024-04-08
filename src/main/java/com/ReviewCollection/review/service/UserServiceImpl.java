package com.ReviewCollection.review.service;

import com.ReviewCollection.review.entity.User;
import com.ReviewCollection.review.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder; // Assuming you have configured password encoder bean

    @Override
    public int getFormsPending(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getFormsPending();
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public int getFormsSubmitted(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getFormsSubmitted();
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public int getTotalForms(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getFormsPending() + user.getFormsSubmitted();
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public ResponseEntity<String> authenticateUser(String usernameOrEmail, String password) {
        // Validate user credentials
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user != null && user.getPassword().equals(password)) {
            // Check if the user has agreed to the terms and conditions
            boolean hasAgreed = hasAgreedToTerms(usernameOrEmail);
            if (!hasAgreed) {
                // User has not agreed to terms and conditions, prevent login
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must agree to the terms and conditions");
            }
            // User has logged in successfully and agreed to terms and conditions
            // Return authentication token or success message
            return ResponseEntity.ok("Login successful");
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Override
    public boolean logoutUser(String usernameOrEmail) {
        return true;
    }

    public boolean hasAgreedToTerms(String usernameOrEmail) {
        // Implement logic to check if the user has agreed to the terms and conditions
        // For demonstration purposes, let's assume the user has agreed
        return true;
    }

}
