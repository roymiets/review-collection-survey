package com.ReviewCollection.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String phone;
    private String bankName;
    private String accountNumber;
    private double loanAmount;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private LocalDate dateOfBirth;
    private String licenseNumber;
    private String licenseState;
    private String ipAddress;
    private String status;
    private LocalDate submittedAt;
    @Override
    public String toString() {
        return "FormData{" +
                "id=" + id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", ssn='" + ssn + '\'' +
                ", phone='" + phone + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", loanAmount=" + loanAmount +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseState='" + licenseState + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", status='" + status + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }

}
