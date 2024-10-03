package com.capstone.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.app.dto.ClientRegistrationDto;
import com.capstone.app.dto.ClientSearchDto;
import com.capstone.app.entity.Client;
import com.capstone.app.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Register a new client
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(
            @RequestPart("client") String clientJson,  // Accepting the JSON as a String
            @RequestPart("certificateFile") MultipartFile certificateFile,
            @RequestPart("companyProfile") MultipartFile companyProfile) throws JsonProcessingException {
        
        // Convert the JSON string to your DTO
        ObjectMapper objectMapper = new ObjectMapper();
        ClientRegistrationDto clientRegistrationDto = objectMapper.readValue(clientJson, ClientRegistrationDto.class);

        System.out.println("Client Registration Data: " + clientRegistrationDto);
        System.out.println("Certificate File: " + certificateFile.getOriginalFilename());
        System.out.println("Company Profile: " + companyProfile.getOriginalFilename());

        // Set the uploaded files in the DTO
        clientRegistrationDto.setCertificateFile(certificateFile);
        clientRegistrationDto.setCompanyProfile(companyProfile);
        
        // Call the service to register the client
        clientService.registerClient(clientRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client registered successfully");
    }

    // Get client by ID
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        Client client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }
    
    // Update client
    @PutMapping("/update/{clientId}")
    public ResponseEntity<String> updateClient(
            @PathVariable Long clientId,
            @RequestBody @Valid ClientRegistrationDto clientRegistrationDto) {
        clientService.updateClient(clientId, clientRegistrationDto);
        return ResponseEntity.ok("Client updated successfully");
    }
    
    // Find clients by company name
    @PostMapping("/find")
    public ResponseEntity<List<Client>> findByCompanyName(@RequestBody @Valid ClientSearchDto searchDto) {
        List<Client> clients = clientService.findByCompanyName(searchDto.getCompanyName());
        return ResponseEntity.ok(clients);
    }
}
