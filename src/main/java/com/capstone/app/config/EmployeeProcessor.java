package com.capstone.app.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.capstone.app.entity.Employee;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Employee process(Employee item) throws Exception {
        // Check if an employee with the same ID exists
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM employees WHERE id = ?",
            new Object[]{item.getId()},
            Integer.class);

        if (count != null && count > 0) {
            System.out.println("Duplicate ID found: " + item.getId());
            return null; // Skip processing for this employee
        }

        return item; // Proceed with processing
    }
}
