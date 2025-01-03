package com.example._2025_bucket.repository;

import com.example._2025_bucket.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
