package com.capstone.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; 
import jakarta.validation.constraints.*; 
import lombok.AllArgsConstructor; 
import lombok.Getter; 
import lombok.NoArgsConstructor; 
import lombok.Setter; 
 
@Entity 
@Table(name = "addresses") 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State can only contain letters and spaces")
    @Column(name = "state", nullable = false)
    private String state;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City can only contain letters and spaces")
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull(message = "Client is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false, unique = true)
    @JsonIgnore
    private Client client;

    // Additional Constructors (if needed)
    public Address(String state, String city, Client client) {
        this.state = state;
        this.city = city;
        this.client = client;
    }
}


