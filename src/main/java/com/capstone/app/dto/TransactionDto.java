package com.capstone.app.dto; 
 
import java.math.BigDecimal; 
import java.time.LocalDateTime; 
 
import com.capstone.app.entity.ClientStatusType; 
 
import jakarta.persistence.Column; 
import jakarta.persistence.EnumType; 
import jakarta.persistence.Enumerated; 
import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Pattern; 
import jakarta.validation.constraints.Size; 
 
public class TransactionDto { 
  
    @NotBlank(message = "Company name cannot be blank") 
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters") 
    @Column(name = "companyName") 
    private String companyName; 
 
    @NotBlank(message = "Employee name cannot be blank") 
    @Size(min = 2, max = 100, message = "Employee name must be between 2 and 100 characters") 
    @Column(name = "employeeName") 
    private String employeeName; 
 
    @NotBlank(message = "Email ID cannot be blank") 
    @Email(message = "Email should be valid") 
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format") 
    @Column(name = "emailId") 
    private String emailId; 
 
    @NotNull(message = "Amount cannot be null") 
    @Min(value = 1, message = "Amount must be greater than 0") 
    @Column(name = "amount") 
    private BigDecimal amount; 
 
    @Enumerated(EnumType.STRING) 
    @NotNull(message = "Client status is required") 
    @Column(name = "userstatus", nullable = false) 
    private ClientStatusType clientstatus; 
 
    @NotNull(message = "GeneratedAt cannot be null") 
    @Column(name = "generatedAt") 
    private LocalDateTime generatedAt; 
 
    @Column(name = "checklist") 
    private boolean checklist; 
 
}