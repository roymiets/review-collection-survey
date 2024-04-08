package com.ReviewCollection.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String email;
    private String mobileNumber;
   // private String msgBody;
   // private String subject;
    private String otp;
    private String password;
    private boolean isVerified;
    private boolean isActive;
    @Column
    private LocalDateTime loginTime;
    @Column
    private LocalDateTime logoutTime;
    private int formsSubmitted;
    private int formsPending;
    private String agreementTerms;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    public User(String recipient,String otp) {
        this.email=recipient;
        this.otp=otp;
    }
}
