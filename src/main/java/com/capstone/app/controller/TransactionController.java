package com.capstone.app.controller; 
 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController; 
 
import com.capstone.app.entity.Transaction; 
import com.capstone.app.service.TransactionService; 
 
@RestController 
@RequestMapping("/api/transactions") // Base URL for the controller 
public class TransactionController { 
 
    private final TransactionService transactionService; 
 
    @Autowired 
    public TransactionController(TransactionService transactionService) { 
        this.transactionService = transactionService; 
    } 
 
    @GetMapping // Map GET requests to /api/transactions 
    public ResponseEntity<List<Transaction>> viewAllTransactions( 
            @RequestParam(defaultValue = "0") int pageNo,  
            @RequestParam(defaultValue = "10") int pageSize) { 
        List<Transaction> transactions = transactionService.getAllTransactions(pageNo, pageSize); // Fetch all transactions with pagination 
        return ResponseEntity.ok(transactions); // Return transactions with a 200 OK status 
    } 
}