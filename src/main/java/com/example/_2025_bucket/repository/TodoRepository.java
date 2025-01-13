package com.example._2025_bucket.repository;

import com.example._2025_bucket.entity.Category;
import com.example._2025_bucket.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserId(long id);
    List<Todo> findByCategoryId(int categoryId);

    Page<Todo> findAllBy(Pageable pageable);

    Page<Todo> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT distinct q from Todo q WHERE q.content like %:kw%")
    Page<Todo> findAllBykeyword(Pageable pageable, String kw);

    @Query("SELECT distinct q from Todo q WHERE q.category.id = :id and q.content like %:kw%")
    Page<Todo> findAllBykeywordAndCategory(int id, Pageable pageable, String kw);
}
