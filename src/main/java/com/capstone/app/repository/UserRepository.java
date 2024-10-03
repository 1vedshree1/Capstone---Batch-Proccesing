package com.capstone.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
