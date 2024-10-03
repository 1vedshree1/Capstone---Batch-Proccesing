package com.capstone.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Document {
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "doc_id")   
    private Long docId;   

    @NotBlank(message = "Document name is required")
    @Column(name = "doc_name", nullable = false)   
    private String docName;   

    @NotBlank(message = "Document URL is required")
    @Column(name = "url", nullable = false)
    private String url; // Store a single URL for this document type

    @ManyToOne   
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)   
    private Client client;   
}
