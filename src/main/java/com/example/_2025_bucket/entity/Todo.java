package com.example._2025_bucket.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean check_complete;
    private String content;
    private LocalDate goal_day;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;
    private String image_path;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @Builder
    public Todo(long id, boolean check_complete, String content, LocalDate goal_day,
                LocalDateTime create_at, LocalDateTime modified_at,
                User user,  List<Review> reviews, String image_path) {
        this.id = id;
        this.content = content;
        this.goal_day = goal_day;
        this.create_at = create_at;
        this.modified_at = modified_at;
        this.check_complete = check_complete;
        this.user = user;
        this.reviews = reviews;
        this.image_path = image_path;
    }
}
