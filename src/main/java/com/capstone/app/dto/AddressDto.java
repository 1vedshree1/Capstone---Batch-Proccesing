package com.capstone.app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AddressDto {
	private int id; 
	
	@NotBlank(message = "State is required")
    private String state; 
	
	@NotBlank(message = "City is required")
    private String city;

}
