package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Review;
import com.example._2025_bucket.entity.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
public class ReviewDto {
    private long id;
    private String content;
    private LocalDateTime create_at;
    private Todo todo;

    public Review toEntity(){
        return Review.builder()
                .content(this.content)
                .create_at(this.create_at)
                .id(this.id)
                .todo(this.todo)
                .build();
    }

    public void setModified_at(LocalDateTime now) {
    }
}
