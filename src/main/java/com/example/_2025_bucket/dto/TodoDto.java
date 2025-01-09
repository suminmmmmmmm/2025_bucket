package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Category;
import com.example._2025_bucket.entity.Review;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private long id;
    private boolean checkComplete;
    private String content;
    private LocalDate goalDay;
    private LocalDateTime uploadAt;
    private LocalDateTime modifiedAt;
    private User user;
    private List<Review> reviews;
    private String imagePath;
    private Category category;
    private String nickname; // 닉네임 추가

    public Todo toEntity(){
        return Todo.builder()
                .id(this.id)
                .checkComplete(this.checkComplete)
                .content(this.content)
                .goalDay(this.goalDay)
                .uploadAt(this.uploadAt)
                .modifiedAt(this.modifiedAt)
                .user(this.user)
                .reviews(this.reviews)
                .imagePath(this.imagePath)
                .category(this.category)
                .build();
    }
}
