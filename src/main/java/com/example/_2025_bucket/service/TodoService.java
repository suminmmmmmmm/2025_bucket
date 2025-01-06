package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 데이터베이스에서 Todo 리스트 가져오기
    public List<TodoDto> getTodoList() {
        List<Todo> todos = todoRepository.findAll();

        // 엔티티 → DTO 변환
        return todos.stream()
                .map(todo -> TodoDto.builder()
                        .id(todo.getId())
                        .content(todo.getContent()) // 제목
                        .imagePath(todo.getImagePath()) // 이미지 경로
                        .build())
                .collect(Collectors.toList());
    }

}
