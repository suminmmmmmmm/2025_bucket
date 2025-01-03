package com.example._2025_bucket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "UserTbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
}
