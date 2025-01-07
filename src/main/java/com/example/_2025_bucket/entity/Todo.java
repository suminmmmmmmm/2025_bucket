package com.example._2025_bucket.entity;

import com.example._2025_bucket.entity.Review;
import jakarta.persistence.*;
import jdk.jfr.Category;
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

    @Column(nullable = false)
    private boolean check_complete;

    @Column(nullable = false)
    private String content;

    private LocalDate goal_day;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // User는 반드시 설정되어야 함
    private User user;



    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @Builder
    public Todo(long id, boolean check_complete, String content, LocalDate goal_day,
                LocalDateTime create_at, LocalDateTime modified_at,
                User user, List<Review> reviews, String imagePath,Category category) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        this.id = id;
        this.check_complete = check_complete;
        this.content = content;
        this.goal_day = goal_day;
        this.create_at = create_at;
        this.modified_at = modified_at;
        this.user = user;
        this.reviews = reviews;

    }
}
