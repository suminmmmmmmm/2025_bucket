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
                    .categoryDto(todo.getCategory().toDto())
                    .userDto(todo.getUser().toDto())
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

    // 어디서 사용하는지 안나와있음, 아마 페이징 추가하면서 안쓰는 것 같음
//    public List<TodoDto> getAllTodos() {
//        List<Todo> todos = this.todoRepository.findAll(); // Todo 엔티티 리스트 조회
//        List<TodoDto> todoDtos = new ArrayList<>();
//
//        for (Todo todo : todos) {
//            // Todo의 User 필드에서 nickname 추출
//            String nickname = (todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown";
//
//            todoDtos.add(TodoDto.builder()
//                    .categoryDto(todo.getCategory().toDto())
//                    .userDto(todo.getUser().toDto())
//                    .reviews(todo.getReviews())
//                    .imagePath(todo.getImagePath())
//                    .checkComplete(todo.isCheckComplete())
//                    .modifiedAt(todo.getModifiedAt())
//                    .content(todo.getContent())
//                    .goalDay(todo.getGoalDay())
//                    .uploadAt(todo.getUploadAt())
//                    .id(todo.getId())
//                    .nickname(nickname) // 닉네임 추가
//                    .build());
//        }
//        return todoDtos;
//    }


    public void update(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void save(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void remove(TodoDto todoDto) {
        Optional<Todo> ot = this.todoRepository.findById((todoDto.getId()));
        if(ot.isPresent()){
            this.todoRepository.delete(todoDto.toEntity());
        }
        else {
            throw new NoSuchElementException("해당 Todo 없음");
        }
    }

    public List<TodoDto> getTodosByUserId(long id) {
        List<Todo> todos =  this.todoRepository.findByUserId(id);
        List<TodoDto> todoDtos = new ArrayList<>();
        for(Todo todo : todos){
            todoDtos.add(TodoDto.builder()
                            .categoryDto(todo.getCategory().toDto())
                            .userDto(todo.getUser().toDto())
                            .reviews(todo.getReviews())
                            .imagePath(todo.getImagePath())
                            .checkComplete(todo.isCheckComplete())
                            .modifiedAt(todo.getModifiedAt())
                            .content(todo.getContent())
                            .goalDay(todo.getGoalDay())
                            .uploadAt(todo.getUploadAt())
                            .id(todo.getId())
                            .nickname((todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown")
                    .build());
        }
        return todoDtos;

    }

    // 페이징 기능 추가로 미사용
//    public List<TodoDto> getTodosByCategory(int categoryId) {
//        List<Todo> todos = todoRepository.findByCategoryId(categoryId); // 카테고리에 해당하는 TODO 조회
//        return todos.stream()
//                .map(todo -> TodoDto.builder()
//                        .categoryDto(todo.getCategory().toDto())
//                        .userDto(todo.getUser().toDto())
//                        .reviews(todo.getReviews())
//                        .imagePath(todo.getImagePath())
//                        .checkComplete(todo.isCheckComplete())
//                        .modifiedAt(todo.getModifiedAt())
//                        .content(todo.getContent())
//                        .goalDay(todo.getGoalDay())
//                        .uploadAt(todo.getUploadAt())
//                        .id(todo.getId())
//                        .nickname((todo.getUser() != null) ? todo.getUser().getNickname() : "Unknown")
//                        .build())
//                .collect(Collectors.toList());
//    }

    // 카테고리별 페이징 기능
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
                        .categoryDto(todo.getCategory().toDto())
                        .userDto(todo.getUser().toDto())
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