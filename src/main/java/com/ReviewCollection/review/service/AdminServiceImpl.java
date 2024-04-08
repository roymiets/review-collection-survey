package com.ReviewCollection.review.service;

import com.ReviewCollection.review.dto.UserDto;
import com.ReviewCollection.review.entity.User;
import com.ReviewCollection.review.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public User createUser(UserDto userDto) {
        User u=new User();
       u.setUsername(userDto.getRecipient());
      u.setOtp(userDto.getOtp());
        return userRepository.save(u);
    }

    @Override
    public User updateUser(Long userId, User user) {
        // Retrieve the user from the database by ID
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user details with the new values
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        // Update other fields as needed

        // Save the updated user entity back to the database
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void approveUser(Long userId) {
        // Retrieve the user from the database by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user's approval status
        user.setVerified(true);

        // Save the updated user entity back to the database
        userRepository.save(user);
    }

    @Override
    public void blockUser(Long userId) {
        // Retrieve the user from the database by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user's block status
        user.setActive(false);

        // Save the updated user entity back to the database
        userRepository.save(user);
    }

    @Override
    public void sendEmailAfterInput(Long userId) {

    }

    @Override
    public void verifyUser(Long userId) {

    }
    @Override
    public void logLoginTime(Long userId) {
        // Retrieve the user from the database by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user's login time
        user.setLoginTime(LocalDateTime.now());

        // Save the updated user entity back to the database
        userRepository.save(user);
    }

    @Override
    public void logLogoutTime(Long userId) {
        // Retrieve the user from the database by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the user's logout time
        user.setLogoutTime(LocalDateTime.now());

        // Save the updated user entity back to the database
        userRepository.save(user);
    }

    @Override
    public User getUserActivity(Long userId) {
        // Retrieve the user from the database by ID
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }


    @Override
    public boolean verifyOTP(String email, String otp) {
        // Verify OTP
        User userEntity = userRepository.findByEmail(email);

        // If user entity is found, retrieve the stored OTP from the entity
        if (userEntity != null) {
            String storedOTP = userEntity.getOtp();

            // Verify if the provided OTP matches the stored OTP
            return otp.equals(storedOTP);
        } else {
            // User with the provided email not found, OTP verification fails
            return false;
        }
    }
    @Override
    public String sendOTP(String recipient, String subject, String otp) {
        // Construct a UserDto object with recipient email, subject, and OTP
        userRepository.save(new User(recipient,otp));
        UserDto userDto = new UserDto();
        userDto.setRecipient(recipient);
       // userDto.setSubject(subject);
        userDto.setOtp("Your OTP is: " + otp);
        // Call the sendSimpleMail method with the constructed UserDto object
        return emailService.sendSimpleMail(userDto);
    }

    @Override
    public boolean sendCredentials(String email, String username, String password) {
        // Construct a UserDto object with recipient email, subject, and message body
        UserDto userDto = new UserDto();
        userDto.setRecipient(email);
        userDto.setSubject("Your Credentials");
        userDto.setOtp("Username: " + username + "\nPassword: " + password);

        // Call the sendSimpleMail method with the constructed UserDto object
        String result = emailService.sendSimpleMail(userDto);

        // Check if the email was sent successfully and return the result
        return "Mail Sent Successfully...".equals(result);
    }


    @Override
    public void saveCredentials(String email, String username, String password) {
// Save username and password in the database
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
    @Override
    public boolean updateUserCredentials(String email, String username, String password) {
        // Retrieve the user entity from the database using the email
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the user entity with the new username and password
            user.setUsername(username);
            user.setPassword(password);
            // Save the updated user entity back to the database
            userRepository.save(user);
            return true; // Return true indicating successful update
        }
        return false; // Return false if the user does not exist
    }
    public void deleteOTP(String email) {
        // Retrieve the user entity from the database using the email
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Clear the OTP field in the user entity
            user.setOtp(null);
            // Save the updated user entity back to the database
            userRepository.save(user);
        }
    }


}