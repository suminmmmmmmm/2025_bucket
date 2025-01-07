package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private int id;              // 카테고리 ID
    private String name;         // 카테고리 이름
    private String description;  // 카테고리 설명

    // 기본 생성자
    public CategoryDto() {}

    // 매개변수 생성자
    public CategoryDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Entity → DTO 변환
    public static CategoryDto fromEntity(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
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