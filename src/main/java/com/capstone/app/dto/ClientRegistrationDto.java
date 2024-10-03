package com.capstone.app.dto;

import org.springframework.web.multipart.MultipartFile;

import com.capstone.app.entity.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ClientRegistrationDto {
    
    @NotBlank(message = "Company name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Company name should contain only alphabets and spaces")
    private String companyName;
    
    @NotBlank(message = "Registration number is required")
    @Size(min = 8, max = 20, message = "Registration number should be between 8 and 20 characters")
    private String registrationNumber;
    
    @NotBlank(message = "Founder name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Founder name should contain only alphabets and spaces")
    private String founderName;
    
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Account number should contain only digits")
    private String accountNumber;
    
    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Za-z]{4}[0-9]{7}$", message = "Invalid IFSC code format")
    private String ifscCode;
    
    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 15, message = "Username should be between 5 and 15 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    
    
   
    private String adminComment;
    
    private MultipartFile certificateFile; // Change to MultipartFile
    private MultipartFile companyProfile; // Change to MultipartFile
    
    

}
