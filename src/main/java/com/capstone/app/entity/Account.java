package com.capstone.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; 
import jakarta.validation.constraints.*; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter; 
import lombok.RequiredArgsConstructor;
import lombok.Setter;  
 
@Entity 
@Table(name = "bank_accounts") 
@Data 
@RequiredArgsConstructor 
@AllArgsConstructor
public class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Account number is required")
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Account number must contain only digits")
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @NotBlank(message = "IFSC code is required")
    @Size(min = 11, max = 11, message = "IFSC code must be exactly 11 characters")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @NotNull(message = "Client is required")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false, unique = true)
    @JsonIgnore
    private Client client;

    // Additional Constructors (if needed)
    public Account(String accountNumber, String ifscCode, Client client) {
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.client = client;
    }
}