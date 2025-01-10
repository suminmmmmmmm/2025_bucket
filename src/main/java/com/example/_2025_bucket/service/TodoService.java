package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Category;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.CategoryRepository;
import com.example._2025_bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
                    .category(todo.getCategory())
                    .user(todo.getUser())
                    .reviews(todo.getReviews())
                    .imagePath(todo.getImagePath())
                    .checkComplete(todo.isCheckComplete())
                    .modifiedAt(todo.getModifiedAt())
                    .content(todo.getContent())
                    .goalDay(todo.getGoalDay())
                    .uploadAt(todo.getUploadAt())
                    .id(todo.getId())
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
                    .category(todo.getCategory())
                    .user(todo.getUser())
                    .reviews(todo.getReviews())
                    .imagePath(todo.getImagePath())
                    .checkComplete(todo.isCheckComplete())
                    .modifiedAt(todo.getModifiedAt())
                    .content(todo.getContent())
                    .goalDay(todo.getGoalDay())
                    .uploadAt(todo.getUploadAt())
                    .id(todo.getId())
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
                        .category(todo.getCategory())
                        .user(todo.getUser())
                        .reviews(todo.getReviews())
                        .imagePath(todo.getImagePath())
                        .checkComplete(todo.isCheckComplete())
                        .modifiedAt(todo.getModifiedAt())
                        .content(todo.getContent())
                        .goalDay(todo.getGoalDay())
                        .uploadAt(todo.getUploadAt())
                        .id(todo.getId())
                        .nickname((todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown")
                        .build())
                .collect(Collectors.toList());
    }

    public Page<TodoDto> getPageTodo(int page, int id){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("uploadAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Todo> todos;

        if(id == 0){
            todos = this.todoRepository.findAllBy(pageable);
        }
        else {
            todos = this.todoRepository.findByCategoryId(id, pageable);
        }

        List<TodoDto> todoDtos = todos.getContent().stream().map(
                todo -> TodoDto.builder()
                        .category(todo.getCategory())
                        .user(todo.getUser())
                        .reviews(todo.getReviews())
                        .imagePath(todo.getImagePath())
                        .checkComplete(todo.isCheckComplete())
                        .modifiedAt(todo.getModifiedAt())
                        .content(todo.getContent())
                        .goalDay(todo.getGoalDay())
                        .uploadAt(todo.getUploadAt())
                        .id(todo.getId())
                        .nickname((todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown")
                        .build()
        ).toList();

        return new PageImpl<>(todoDtos, todos.getPageable(), todos.getTotalElements());
    }
}