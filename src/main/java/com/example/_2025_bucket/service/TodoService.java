package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoDto getTodo(long id) throws Exception{
        Optional<Todo> ot = todoRepository.findById(id);
        if(ot.isPresent()){
            Todo todo = ot.get();
            return TodoDto.builder()
                    .id(todo.getId())
                    .check_complete(todo.isCheck_complete())
                    .user(todo.getUser())
                    .reviews(todo.getReviews())
                    .content(todo.getContent())
                    .create_at(todo.getCreate_at())
                    .modified_at(todo.getModified_at())
                    .goal_day(todo.getGoal_day())
                    .image_path(todo.getImage_path())
                    .category(todo.getCategory())
                    .build();
        }
        else {
            System.out.println("해당 TODO 없음");
            throw new NoSuchElementException("해당 Todo없음");
        }
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = this.todoRepository.findAll(); // Todo 엔티티 리스트 조회
        List<TodoDto> todoDtos = new ArrayList<>();

        for (Todo todo : todos) {
            // Todo의 User 필드에서 nickname 추출
            String nickname = (todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown";

            todoDtos.add(TodoDto.builder()
                    .image_path(todo.getImage_path())
                    .user(todo.getUser()) // User 객체 포함
                    .id(todo.getId())
                    .content(todo.getContent())
                    .reviews(todo.getReviews())
                    .check_complete(todo.isCheck_complete())
                    .create_at(todo.getCreate_at())
                    .modified_at(todo.getModified_at())
                    .goal_day(todo.getGoal_day())
                    .category(todo.getCategory())
                    .nickname(nickname) // 닉네임 추가
                    .build());
        }
        return todoDtos;
    }


    public void update(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void save(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void remove(TodoDto todoDto) {
        this.todoRepository.delete(todoDto.toEntity());
    }

    public List<Todo> getTodosByUserId(long id) {
        return this.todoRepository.findByUserId(id);
    }

    public List<TodoDto> getTodosByCategory(int categoryId) {
        List<Todo> todos = todoRepository.findByCategoryId(categoryId); // 카테고리에 해당하는 TODO 조회
        return todos.stream()
                .map(todo -> TodoDto.builder()
                        .id(todo.getId())
                        .check_complete(todo.isCheck_complete())
                        .content(todo.getContent())
                        .goal_day(todo.getGoal_day())
                        .create_at(todo.getCreate_at())
                        .modified_at(todo.getModified_at())
                        .user(todo.getUser())
                        .reviews(todo.getReviews())
                        .image_path(todo.getImage_path())
                        .category(todo.getCategory())
                        .build())
                .collect(Collectors.toList());
    }
}