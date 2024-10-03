package com.capstone.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.app.entity.Account;
import com.capstone.app.entity.Client;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByClient(Client client);


}
