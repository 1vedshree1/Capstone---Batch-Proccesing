package com.capstone.app.service;

import java.util.List;
import java.util.Optional;

import com.capstone.app.dto.ClientRegistrationDto;
import com.capstone.app.entity.Client;

public interface ClientService {
	void registerClient(ClientRegistrationDto clientRegistrationDto); 
	 List<Client> getAllClients(int pageNo, int pageSize); 
	 Client getClientById(Long clientId); 
	 void updateClient(Long clientId, ClientRegistrationDto clientRegistrationDto); 
	 List<Client> findByCompanyName(String companyName); 
}
