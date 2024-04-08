package com.ReviewCollection.review.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    int getFormsPending(Long userId);

    int getFormsSubmitted(Long userId);

    int getTotalForms(Long userId);
    ResponseEntity<String> authenticateUser(String usernameOrEmail, String password);

    boolean logoutUser(String usernameOrEmail);

}
