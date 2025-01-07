package com.example._2025_bucket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private int id;
    private String name;

    @Builder
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
