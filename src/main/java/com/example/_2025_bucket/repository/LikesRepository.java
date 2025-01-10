package com.example._2025_bucket.repository;

import com.example._2025_bucket.entity.Likes;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndTodo(User user, Todo todo);
    List<Likes> findAllByTodo(Todo todo);
}
