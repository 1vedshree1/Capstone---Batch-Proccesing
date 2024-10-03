package com.capstone.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientSearchDto {
    @NotBlank(message = "Company name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Company name should contain only alphabets and spaces")
    private String companyName;
}

