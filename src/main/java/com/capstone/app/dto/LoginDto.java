package com.capstone.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @jakarta.validation.constraints.NotBlank(message = "Username cannot be empty") 
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters") 
    @Pattern(regexp = "^[a-zA-Z0-9._-]*$", message = "Username can only contain letters, numbers, dots, underscores, and hyphens") 
    private String username; 
 
    @NotBlank(message = "Password cannot be empty") 
    @Size(min = 8, message = "Password must be at least 8 characters") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",  
             message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character") 
    private String password; 
}
