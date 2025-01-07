package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.CategoryDto;
import com.example._2025_bucket.entity.Category;
import com.example._2025_bucket.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 모든 카테고리 조회
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDto::fromEntity) // Entity → DTO 변환
                .collect(Collectors.toList());
    }

    // ID로 카테고리 조회
    public CategoryDto getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        return CategoryDto.fromEntity(category); // Entity → DTO 변환
    }

    // 카테고리 저장
    public void saveCategory(CategoryDto categoryDto) {
        Category category = categoryDto.toEntity(); // DTO → Entity 변환
        categoryRepository.save(category); // 데이터베이스에 저장
    }

    // 카테고리 삭제
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}