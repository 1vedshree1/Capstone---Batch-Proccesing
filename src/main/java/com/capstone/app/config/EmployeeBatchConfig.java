package com.capstone.app.config;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.capstone.app.entity.Employee;

@Configuration
public class EmployeeBatchConfig {

    @Bean 
    public FlatFileItemReader<Employee> readEmployeeCsv() { 
        FlatFileItemReader<Employee> employeeCsvReader = new FlatFileItemReader<>(); 
        employeeCsvReader.setResource(new ClassPathResource("data.csv")); 
        employeeCsvReader.setName("employeeCsvReader"); 
        employeeCsvReader.setLinesToSkip(1); 
        employeeCsvReader.setLineMapper(lineMapper()); 
        return employeeCsvReader; 
    } 

    private LineMapper<Employee> lineMapper() { 
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>(); 
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); 
        lineTokenizer.setDelimiter(","); 
        lineTokenizer.setStrict(false); 
        lineTokenizer.setNames("id","firstName", "lastName", "email", "designation", "department", "salary"); 

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>(); 
        fieldSetMapper.setTargetType(Employee.class); 

        lineMapper.setLineTokenizer(lineTokenizer); 
        lineMapper.setFieldSetMapper(fieldSetMapper); 
        return lineMapper; 
    }
    
    @Bean
    public EmployeeProcessor employeeProcessor() {
        return new EmployeeProcessor();
    }
    
    @Bean 
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) { 
        return new JdbcBatchItemWriterBuilder<Employee>() 
                .sql("INSERT INTO employees (id, first_name, last_name, email, designation, department, salary) VALUES (:id, :firstName, :lastName, :email, :designation, :department, :salary)") 
                .dataSource(dataSource) 
                .beanMapped() 
                .build(); 
    }

    
    @Bean 
    public DataSourceTransactionManager transactionManager(DataSource data) { 
        return new DataSourceTransactionManager(data); 
    } 
    
    @Bean 
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager, 
                      FlatFileItemReader<Employee> reader, EmployeeProcessor processor, JdbcBatchItemWriter<Employee> writer) { 
        return new StepBuilder("importEmployee", jobRepository) 
                .<Employee, Employee>chunk(2, transactionManager) 
                .reader(reader) 
                .processor(processor) 
                .writer(writer) 
                .build(); 
    } 
    
    @Bean 
    @Primary
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListner listener) { 
        return new JobBuilder("importUserJob", jobRepository) 
                .listener(listener) 
                .start(step1) 
                .build(); 
    }
    
    @Bean
    public JdbcCursorItemReader<Employee> databaseReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Employee>()
                .dataSource(dataSource)
                .name("databaseReader")
                .sql("SELECT id, first_name, last_name, email, designation, department, salary FROM employees") 
                .rowMapper((rs, rowNum) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("designation"),
                        rs.getString("department"),
                        rs.getBigDecimal("salary")
                ))
                .build();
    }
    
    @Bean
    public FlatFileItemWriter<Employee> csvWriter() {
        return new FlatFileItemWriterBuilder<Employee>()
                .name("csvWriter")
                .resource(new FileSystemResource("output.csv"))
                .lineAggregator(new DelimitedLineAggregator<Employee>() {{
                    setDelimiter(",");
                    setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {{
                        setNames(new String[] { "firstName", "lastName", "email", "designation", "department", "salary" });
                    }});
                }})
                .build();
    }
    
    @Bean
    public Step exportStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                          JdbcCursorItemReader<Employee> databaseReader, FlatFileItemWriter<Employee> csvWriter) {
        return new StepBuilder("exportEmployee", jobRepository)
                .<Employee, Employee>chunk(10, transactionManager)
                .reader(databaseReader)
                .writer(csvWriter)
                .build();
    }
    
    @Bean
    public Job exportJob(JobRepository jobRepository, Step exportStep) {
        return new JobBuilder("exportJob", jobRepository)
                .start(exportStep)
                .build();
    }
}
