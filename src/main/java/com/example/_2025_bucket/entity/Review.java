package com.example._2025_bucket.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private LocalDateTime create_at;
    private LocalDateTime modify_at;
    @ManyToOne
    private Todo todo;
    @ManyToOne
    private User user;

    @Builder
    public Review(long id, String content, LocalDateTime create_at, Todo todo, LocalDateTime modify_at
    , User user) {
        this.id = id;
        this.content = content;
        this.create_at = create_at;
        this.todo = todo;
        this.modify_at = modify_at;
        this.user = user;
    }
}
