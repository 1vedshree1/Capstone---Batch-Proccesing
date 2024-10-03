package com.capstone.app.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.app.entity.Employee; 
 
public interface EmployeeService { 
 
 List<Employee> getAllEmployees(int page, int size); 
 
}
