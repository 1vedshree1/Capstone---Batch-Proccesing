package com.capstone.app.repository; 
 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import com.capstone.app.entity.Transaction; 
 
@Repository 
public interface TransactionRepository extends JpaRepository<Transaction, Integer> { 
     
}