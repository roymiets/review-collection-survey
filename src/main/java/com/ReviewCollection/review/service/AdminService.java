package com.ReviewCollection.review.service;

import com.ReviewCollection.review.dto.UserDto;
import com.ReviewCollection.review.entity.User;

import java.util.List;

public interface AdminService {
    User createUser(UserDto userDto);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    List<User> getAllUsers();
    User getUserById(Long userId);
    void approveUser(Long userId);
    void blockUser(Long userId);
   // void inputDataManually(Data data);
    void sendEmailAfterInput(Long userId);
    void verifyUser(Long userId);
    void logLoginTime(Long userId);
    void logLogoutTime(Long userId);

    User getUserActivity(Long userId);

    String sendOTP(String email, String mobileNumber, String otp);

    boolean verifyOTP(String email, String otp);

    void saveCredentials(String email, String username, String password);

    boolean sendCredentials(String email, String username, String password);

    boolean updateUserCredentials(String email, String username, String password);

    void deleteOTP(String email);



    // UserActivity getUserActivity(Long userId);
}
