package com.capstone.app.controller; 
 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
@RestController 
@RequestMapping("/api/emails") 
public class EmailController { 
 
    @PostMapping("/register") 
    public ResponseEntity<String> registerEmail(@RequestBody String email) { 
        // Email registration logic here 
        return ResponseEntity.ok("Email registered successfully."); 
    } 
 
    // Other email-related methods 
}