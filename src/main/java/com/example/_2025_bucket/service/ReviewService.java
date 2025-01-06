package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.ReviewDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Review;
import com.example._2025_bucket.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void create(TodoDto todoDto, String content) {
        ReviewDto reviewDto = ReviewDto.builder()
                .content(content)
                .create_at(LocalDateTime.now())
                .todo(todoDto.toEntity())
                .build();
        reviewRepository.save(reviewDto.toEntity());
    }

    public ReviewDto getReview(long id) {
        Review review = this.reviewRepository.getReviewById(id);
            return ReviewDto.builder()
                    .todo(review.getTodo())
                    .content(review.getContent())
                    .create_at(review.getCreate_at())
                    .id(id)
                    .build();
    }

    public void update(ReviewDto reviewDto) {
        Review review = this.reviewRepository.save(reviewDto.toEntity());
    }

    public void delete(ReviewDto reviewDto) {
        Review review = this.reviewRepository.getReviewById(reviewDto.getId());
        this.reviewRepository.delete(review);
    }
}
