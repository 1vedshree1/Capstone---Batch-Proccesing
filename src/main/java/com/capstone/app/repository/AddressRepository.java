package com.capstone.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.app.entity.Address;
import com.capstone.app.entity.Client;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	Optional<Address> findByClient(Client client);


}
