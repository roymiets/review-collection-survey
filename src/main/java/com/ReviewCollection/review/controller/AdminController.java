package com.ReviewCollection.review.controller;

import com.ReviewCollection.review.dto.UserDto;
import com.ReviewCollection.review.entity.User;
import com.ReviewCollection.review.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody UserDto createUserRequest) {
        // Validate input data (assuming validation is done elsewhere)

        // Generate OTP
        String otp = generateOTP();

        // Send OTP to user's email
        boolean otpSent = Boolean.parseBoolean(adminService.sendOTP(createUserRequest.getRecipient(), createUserRequest.getSubject(), otp));

        if (!otpSent) {
            return ResponseEntity.ok("OTP sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP");
        }
    }


//    @PostMapping("/verify-otp")
//    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp) {
//        // Verify OTP
//        boolean otpVerified = adminService.verifyOTP(email, otp);
//        if (otpVerified) {
//            // Generate username and password
//            String username = generateUsername();
//            String password = generatePassword();
//            // Save username and password in the database
//            adminService.saveCredentials(email, username, password);
//            // Send username and password to user's email
//            boolean credentialsSent = adminService.sendCredentials(email, username, password);
//            if (credentialsSent) {
//                return ResponseEntity.ok("Username and password sent successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send username and password");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
//        }
//    }
@PostMapping("/verify-otp")
public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp) {
    // Verify OTP
    boolean otpVerified = adminService.verifyOTP(email, otp);
    if (otpVerified) {
        // Generate username and password
        String username = generateUsername();
        String password = generatePassword();

        // Update user data with username and password
        boolean credentialsUpdated = adminService.updateUserCredentials(email, username, password);
        if (credentialsUpdated) {
            // Delete OTP from database
            adminService.deleteOTP(email);
            // Send username and password to user's email
            boolean credentialsSent = adminService.sendCredentials(email, username, password);
            if (credentialsSent) {
                return ResponseEntity.ok("Username and password sent successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send username and password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user credentials");
        }
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
    }
}



    @PostMapping("/usersCreateManual")
    public ResponseEntity<User> createUserManual(@RequestBody UserDto userDto) {
        User createdUser = adminService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = adminService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/users/{userId}/approve")
    public ResponseEntity<Void> approveUser(@PathVariable Long userId) {
        adminService.approveUser(userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/users/{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long userId) {
        adminService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/data/{userId}/send-email")
    public ResponseEntity<Void> sendEmailAfterInput(@PathVariable Long userId) {
        adminService.sendEmailAfterInput(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/verify")
    public ResponseEntity<Void> verifyUser(@PathVariable Long userId) {
        adminService.verifyUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/login")
    public ResponseEntity<Void> logLoginTime(@PathVariable Long userId) {
        adminService.logLoginTime(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/logout")
    public ResponseEntity<Void> logLogoutTime(@PathVariable Long userId) {
        adminService.logLogoutTime(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/activity")
    public ResponseEntity<User> getUserActivity(@PathVariable Long userId) {
        User userActivity = adminService.getUserActivity(userId);
        return ResponseEntity.ok(userActivity);
    }
    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private String generateUsername() {
        // Generate a random username
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder usernameBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            usernameBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return usernameBuilder.toString();
    }

    private String generatePassword() {
        // Generate a random password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=";
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            passwordBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return passwordBuilder.toString();
    }

}
