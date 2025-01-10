package com.example._2025_bucket.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Todo todo;

    @ManyToOne
    private User user;

    @Builder
    public Likes(long id, Todo todo, User user){
        this.id = id;
        this.todo = todo;
        this.user = user;
    }
}
