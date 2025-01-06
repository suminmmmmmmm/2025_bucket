package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
                    .build();
        }
        else throw new NoSuchElementException("해당 Todo없음");
    }

    public void update(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

}
