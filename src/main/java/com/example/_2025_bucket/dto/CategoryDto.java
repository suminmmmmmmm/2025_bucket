package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Category;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private int id;              // 카테고리 ID
    private String name;         // 카테고리 이름
    private String description;  // 카테고리 설명

    // Entity → DTO 변환
    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    // DTO → Entity 변환
    public Category toEntity() {
        return Category.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .build();
    }
}
