package com.capstone.app.service; 

import java.time.LocalDateTime;  
import java.util.List;
import java.util.Map;
import java.util.Optional;  
 
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.PageRequest;  
import org.springframework.security.crypto.password.PasswordEncoder;  
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.app.dto.AddressDto;  
import com.capstone.app.dto.ClientRegistrationDto;  
import com.capstone.app.entity.Account;  
import com.capstone.app.entity.Address;  
import com.capstone.app.entity.Client;  
import com.capstone.app.entity.ClientStatusType;
import com.capstone.app.entity.Document;
import com.capstone.app.entity.Transaction;  
import com.capstone.app.entity.User;  
import com.capstone.app.entity.UserStatusType;  
import com.capstone.app.repository.AccountRepository;  
import com.capstone.app.repository.AddressRepository;  
import com.capstone.app.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.capstone.app.repository.AccountRepository;  
import com.capstone.app.repository.ClientRepository;  
import com.capstone.app.repository.UserRepository;  
 
import jakarta.persistence.EntityNotFoundException;  
import jakarta.transaction.Transactional;  
 
@Service  
public class ClientServiceImpl implements ClientService {  
private final ClientRepository clientRepository;  
   private final UserRepository userRepository;  
   private final AddressRepository addressRepository;  
   private final AccountRepository accountRepository;  
   private final PasswordEncoder passwordEncoder;  
   private final EmailService emailService; // Inject EmailService 
   private final Cloudinary cloudinary;
   @Autowired  
   public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository,  
                            AddressRepository addressRepository, AccountRepository accountRepository,  
                            PasswordEncoder passwordEncoder,EmailService emailService, Cloudinary cloudinary) {  
       this.clientRepository = clientRepository;  
       this.userRepository = userRepository;  
       this.addressRepository = addressRepository;  
       this.accountRepository = accountRepository;  
       this.passwordEncoder = passwordEncoder;  
       this.emailService = emailService; 
       this.cloudinary = cloudinary;
   }  
 
   private String uploadFileToCloudinary(MultipartFile file) {
       try {
           Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
           return (String) uploadResult.get("secure_url"); // Return the secure URL
       } catch (Exception e) {
           throw new RuntimeException("File upload failed: " + e.getMessage());
       }
   }
   @Override  
   @Transactional  
   public void registerClient(ClientRegistrationDto clientRegistrationDto) {  
       // Create and save the User entity  
       User user = new User();  
       user.setUsername(clientRegistrationDto.getUsername());  
       user.setPassword(passwordEncoder.encode(clientRegistrationDto.getPassword())); // Encode the password  
       user.setUserstatus(UserStatusType.ACTIVE); // Assuming a default active status  
       userRepository.save(user);  
 
       // Create and save the Client entity  
       Client client = new Client();  
       client.setCompanyName(clientRegistrationDto.getCompanyName());  
       client.setRegistrationNumber(clientRegistrationDto.getRegistrationNumber());  
       client.setFounderName(clientRegistrationDto.getFounderName());  
       client.setEmail(clientRegistrationDto.getEmail());  
       client.setCreatedAt(LocalDateTime.now()); // Set current time  
       client.setUser(user); // Set the user reference  
       client.setStatus(ClientStatusType.APPROVED); // Set default status or adjust accordingly  
 
       Document certificateDocument = new Document();
       certificateDocument.setDocName("Certificate File");
       certificateDocument.setUrl(uploadFileToCloudinary(clientRegistrationDto.getCertificateFile())); // Upload and get URL
       client.addDocument(certificateDocument);

       Document companyProfileDocument = new Document();
       companyProfileDocument.setDocName("Company Profile");
       companyProfileDocument.setUrl(uploadFileToCloudinary(clientRegistrationDto.getCompanyProfile())); // Upload and get URL
       client.addDocument(companyProfileDocument);

       client = clientRepository.save(client);  
 
       // Create and save Address entity  
       Address address = new Address();  
       address.setState(clientRegistrationDto.getState());  
       address.setCity(clientRegistrationDto.getCity());  
       address.setClient(client); // Set the client reference  
       addressRepository.save(address); // Save the address  
 
       // Create and save Account entity  
       Account account = new Account();  
       account.setAccountNumber(clientRegistrationDto.getAccountNumber());account.setIfscCode(clientRegistrationDto.getIfscCode());  
       account.setClient(client); // Set the client reference  
       accountRepository.save(account); // Save the account  
 
       sendConfirmationEmail(client); 

         
   }  
   private void sendConfirmationEmail(Client client) { 
       String subject = "Client Registration Successful"; 
       String body = "Dear " + client.getCompanyName() + ",\n\n" 
                   + "Thank you for registering with us. Your registration was successful.\n\n" 
                   + "Best Regards,\nYour Company"; 

       emailService.sendEmail(client.getEmail(), subject, body); 
   } 
     
   @Override  
   public List<Client> getAllClients(int 
pageNo, int pageSize) {  
       Page<Client> clientPage = clientRepository.findAll(PageRequest.of(pageNo, pageSize));  
       return clientPage.getContent(); // Return the content of the page  
   }  
 
   @Override  
   public Client getClientById(Long clientId) {  
       return clientRepository.findById(clientId)  
           .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));  
   }  
     
   @Override  
   @Transactional  
   public void updateClient(Long clientId, ClientRegistrationDto clientRegistrationDto) {  
       // Find the client by id  
       Client client = clientRepository.findById(clientId)  
               .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));  
 
       // Update client details only if they are provided  
       if (clientRegistrationDto.getCompanyName() != null) {  
           client.setCompanyName(clientRegistrationDto.getCompanyName());  
       }  
       if (clientRegistrationDto.getRegistrationNumber() != null) {  
           client.setRegistrationNumber(clientRegistrationDto.getRegistrationNumber());  
       }  
       if (clientRegistrationDto.getFounderName() != null) {  
           client.setFounderName(clientRegistrationDto.getFounderName());  
       }  
       if (clientRegistrationDto.getEmail() != null) {  
           client.setEmail(clientRegistrationDto.getEmail());  
       }  
 
       // Update the address details if provided  
       Address address = addressRepository.findByClient(client)  
               .orElseThrow(() -> new EntityNotFoundException("Address not found for client"));  
       if (clientRegistrationDto.getState() != null) {  
           address.setState(clientRegistrationDto.getState());  
       }  
       if (clientRegistrationDto.getCity() != null) {  
           address.setCity(clientRegistrationDto.getCity());  
       }  
 
       // Update account details if provided  
       Account account = accountRepository.findByClient(client)  
               .orElseThrow(() -> new EntityNotFoundException("Account not found for client"));  
       if (clientRegistrationDto.getAccountNumber() != null) {  
           account.setAccountNumber(clientRegistrationDto.getAccountNumber());  
       }  
       if (clientRegistrationDto.getIfscCode() != null) {  
           account.setIfscCode(clientRegistrationDto.getIfscCode());  
       }  
 
       // Save updated client, address, and account details  
       clientRepository.save(client);  
       addressRepository.save(address);  
       accountRepository.save(account);  
   }  
     
   // Find clients by company name  
   public List<Client> findByCompanyName(String companyName) {  
       return clientRepository.findByCompanyName(companyName); // Assuming a method exists in the repository  
   }  
 
 
 
 
}