package com.capstone.app.controller;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.app.entity.Employee;
import com.capstone.app.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
		@Autowired
		private JobLauncher jobLauncher;
		@Autowired
		
		private Job job;
		 @Autowired
		 
		    private Job exportJob;
		 
		 @GetMapping("/employees") 
		 public List<Employee> getAllEmployees( 
		         @RequestParam(defaultValue = "0") int page, 
		         @RequestParam(defaultValue = "10") int size) { 
		     return employeeService.getAllEmployees(page, size); 
		 } 

		@GetMapping("/process-import")
		public void process() {
			try {
				JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
						.toJobParameters();

				jobLauncher.run(job, jobParameters);
			} catch (JobExecutionAlreadyRunningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobRestartException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobInstanceAlreadyCompleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@GetMapping("/process-export")
	    public String processExport() {
	        try {
	            JobParameters jobParameters = new JobParametersBuilder()
	                    .addLong("startAt", System.currentTimeMillis())
	                    .toJobParameters();

	            jobLauncher.run(exportJob, jobParameters);
	            return "Export job started";
	        } catch (JobExecutionAlreadyRunningException e) {
	            return "Job is already running: " + e.getMessage();
	        } catch (JobRestartException e) {
	            return "Job restart error: " + e.getMessage();
	        } catch (JobInstanceAlreadyCompleteException e) {
	            return "Job instance already complete: " + e.getMessage();
	        } catch (JobParametersInvalidException e) {
	            return "Invalid job parameters: " + e.getMessage();
	        }
	    }
	}


