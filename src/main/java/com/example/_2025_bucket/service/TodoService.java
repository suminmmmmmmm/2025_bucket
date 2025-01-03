package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // TodoDto를 엔티티로 변환하여 저장
    public void createTodo(TodoDto todoDto) {
        Todo todo = todoDto.toEntity(); // DTO → 엔티티 변환
        todoRepository.save(todo);     // 엔티티를 데이터베이스에 저장
    }
}
