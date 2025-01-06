package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Review;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private boolean check_complete;
    private String content;
    private LocalDate goal_day;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;
    private User user;
    private List<Review> reviews;
    //private MultipartFile bucketImage; // MultipartFile 타입으로 변경
    private String imagePath; // 이미지 경로 저장

    public Todo toEntity() {
//        byte[] imageBytes = null;
//
//        try {
//            if (this.bucketImage != null && !this.bucketImage.isEmpty()) {
//                imageBytes = this.bucketImage.getBytes(); // MultipartFile → byte[] 변환
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return Todo.builder()
                .id(this.id)
                .check_complete(this.check_complete)
                .content(this.content)
                .goal_day(this.goal_day)
                .create_at(this.create_at)
                .modified_at(this.modified_at)
                .user(this.user)
                .reviews(this.reviews)
//                .bucketImage(imageBytes) // 변환된 byte[] 데이터 전달
                .imagePath(this.imagePath)
                .build();
    }

}
