package com.capstone.app.service; 
 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest; 
import org.springframework.stereotype.Service; 
 
import com.capstone.app.entity.Employee; 
import com.capstone.app.repository.EmployeeRepository; 
 
@Service 
public class EmployeeServiceImpl implements EmployeeService { 
  
 private final EmployeeRepository employeeRepository;  
   
    @Autowired  
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {  
        this.employeeRepository = employeeRepository;  
    }  
  
    @Override  
    public List<Employee> getAllEmployees(int pageNo, int pageSize) {  
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(pageNo, pageSize));  
        return employeePage.getContent(); // Return the content of the page  
    }  
 
}