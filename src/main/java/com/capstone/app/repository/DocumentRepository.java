package com.capstone.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.app.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
