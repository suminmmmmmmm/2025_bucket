package com.example._2025_bucket.repository;

import com.example._2025_bucket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
