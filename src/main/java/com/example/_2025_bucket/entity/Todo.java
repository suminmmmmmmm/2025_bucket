package com.example._2025_bucket.entity;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Review;
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

    @Column(nullable = false)
    private boolean checkComplete;

    @Column(nullable = false)
    private String content;

    private LocalDate goalDay;
    private LocalDateTime uploadAt;
    private LocalDateTime modifiedAt;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID") // CATEGORY 테이블과 연관
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // User는 반드시 설정되어야 함
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Likes> likes;

    @Builder
    public Todo(long id, boolean checkComplete, String content, LocalDate goalDay,
                LocalDateTime uploadAt, LocalDateTime modifiedAt,
                User user, List<Review> reviews, String imagePath,
                Category category) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        this.id = id;
        this.checkComplete = checkComplete;
        this.content = content;
        this.goalDay = goalDay;
        this.uploadAt = uploadAt;
        this.modifiedAt = modifiedAt;
        this.user = user;
        this.reviews = reviews;
        this.category = category;
        this.imagePath = imagePath;

    }

    public TodoDto toDto(){
        return TodoDto.builder()
                .id(this.id)
                .checkComplete(this.checkComplete)
                .content(this.content)
                .goalDay(goalDay)
                .uploadAt(this.uploadAt)
                .modifiedAt(this.modifiedAt)
                .categoryDto(this.category.toDto())
                .userDto(this.user.toDto())
                .nickname(this.user.getNickname())
                .imagePath(this.imagePath)
                .reviews(this.reviews)
                .build();
    }
}
