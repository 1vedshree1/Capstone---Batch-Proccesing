package com.capstone.app.service; 
 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest; 
import org.springframework.stereotype.Service; 
 
import com.capstone.app.entity.Transaction; 
import com.capstone.app.repository.TransactionRepository; 
 
@Service 
public class TransactionServiceImpl implements TransactionService { 
 
    private final TransactionRepository transactionRepository; 
 
    @Autowired  
    public TransactionServiceImpl(TransactionRepository transactionRepository) { 
        this.transactionRepository = transactionRepository; 
    } 
 
    @Override 
    public List<Transaction> getAllTransactions(int pageNo, int pageSize) { 
        Page<Transaction> transactionPage = transactionRepository.findAll(PageRequest.of(pageNo, pageSize)); 
        return transactionPage.getContent(); // Return the content of the page 
    } 
}