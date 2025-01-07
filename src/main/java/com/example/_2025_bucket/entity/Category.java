package com.example._2025_bucket.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name; // 카테고리 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 카테고리 설명

    @Builder
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}