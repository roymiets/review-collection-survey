package com.ReviewCollection.review.controller;

import com.ReviewCollection.review.entity.FormData;
import com.ReviewCollection.review.service.FormService;
import com.ReviewCollection.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FormService formService;

    @GetMapping("/{userId}/forms/pending")
    public ResponseEntity<Integer> getFormsPending(@PathVariable Long userId) {
        int formsPending = userService.getFormsPending(userId);
        return ResponseEntity.ok(formsPending);
    }

    @GetMapping("/{userId}/forms/submitted")
    public ResponseEntity<Integer> getFormsSubmitted(@PathVariable Long userId) {
        int formsSubmitted = userService.getFormsSubmitted(userId);
        return ResponseEntity.ok(formsSubmitted);
    }

    @GetMapping("/{userId}/forms/total")
    public ResponseEntity<Integer> getTotalForms(@PathVariable Long userId) {
        int totalForms = userService.getTotalForms(userId);
        return ResponseEntity.ok(totalForms);
    }
    @PostMapping("/{userId}/forms")
    public ResponseEntity<FormData> saveForm(@PathVariable Long userId, @RequestBody FormData form) {
        // Set the user ID for the form
        form.setUserId(userId);
        FormData savedForm = formService.saveForm(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedForm);
    }
    public ResponseEntity<List<FormData>> getAllFormsByUserId(@PathVariable Long userId) {
        List<FormData> forms = formService.getAllFormsByUserId(userId);
        return ResponseEntity.ok(forms);
    }
    @GetMapping("/{userId}/forms/{formId}")
    public ResponseEntity<FormData> getFormById(@PathVariable Long userId, @PathVariable Long formId) {
        FormData form = formService.getFormById(formId);
        if (form != null && form.getUserId().equals(userId)) {
            return ResponseEntity.ok(form);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{userId}/forms/{formId}")
    public ResponseEntity<Void> deleteFormById(@PathVariable Long userId, @PathVariable Long formId) {
        FormData form = formService.getFormById(formId);
        if (form != null && form.getUserId().equals(userId)) {
            formService.deleteForm(formId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String usernameOrEmail, @RequestParam String password) {
        // Delegate login logic to the service layer
        return userService.authenticateUser(usernameOrEmail, password);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String usernameOrEmail) {
        // Delegate logout logic to the service layer
        boolean loggedOut = userService.logoutUser(usernameOrEmail);
        if (loggedOut) {
            return ResponseEntity.ok("User logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to logout user");
        }
    }


}
