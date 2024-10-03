package com.capstone.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@NoArgsConstructor  // Added NoArgsConstructor for flexibility
@AllArgsConstructor
@Data 
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotBlank(message = "Registration number is required")
    @Size(min = 5, max = 50, message = "Registration number must be between 5 and 50 characters")
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @NotBlank(message = "Founder name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Founder name can only contain letters and spaces")
    @Column(name = "founder_name", nullable = false)
    private String founderName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(name = "status", nullable = false)
    private ClientStatusType status;

    @NotNull(message = "Created at timestamp is required")
    @PastOrPresent(message = "Created at timestamp must be in the past or present")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Size(max = 500, message = "Admin comment cannot be more than 500 characters")
    @Column(name = "admin_comment")
    private String adminComment;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonManagedReference
    private User user;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Address adress;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>(); // Initialize the list

    public void addDocument(Document document) {
        documents.add(document);
        document.setClient(this);
    }
}
