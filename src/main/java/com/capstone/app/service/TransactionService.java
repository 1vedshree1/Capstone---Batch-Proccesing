package com.capstone.app.service; 
 
import com.capstone.app.entity.Transaction; 
 
import java.util.List; 
 
import org.springframework.data.domain.Page; 
 
public interface TransactionService { 
    List<Transaction> getAllTransactions(int pageNo, int pageSize); 
}