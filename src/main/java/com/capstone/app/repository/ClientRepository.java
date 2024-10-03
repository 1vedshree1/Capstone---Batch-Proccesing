package com.capstone.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.app.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findById(Long id);
	List<Client> findByCompanyName(String companyName);


}
