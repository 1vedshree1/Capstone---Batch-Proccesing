package com.capstone.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.app.entity.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
