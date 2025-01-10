package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.LikeDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.dto.UserDto;
import com.example._2025_bucket.entity.Likes;
import com.example._2025_bucket.entity.Likes;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import com.example._2025_bucket.repository.LikesRepository;
import com.example._2025_bucket.repository.TodoRepository;
import com.example._2025_bucket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikesRepository likeRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    public LikeDto getLikeByTodoAndUser(TodoDto todoDto, UserDto userDto){
        Optional<Todo> ot = this.todoRepository.findById(todoDto.getId());
        Optional<User> ou = this.userRepository.findByEmail(userDto.getEmail());
        if(ot.isPresent() && ou.isPresent()){
            Todo todo = ot.get();
            User user = ou.get();
            Optional<Likes> ol = this.likeRepository.findByUserAndTodo(user, todo);
            if(ol.isPresent()){
                Likes likes = ol.get();
                return LikeDto.builder()
                        .id(likes.getId())
                        .todo(likes.getTodo().toDto())
                        .user(likes.getUser().toDto())
                        .build();
            }
            else return null;
        }
        else return null;
    }

    public void create(LikeDto likeDto) {

        this.likeRepository.save(likeDto.toEntity());
    }

    public long countLikeByTodoId(long id){
        Optional<Todo> ot = this.todoRepository.findById(id);
        if(ot.isPresent()){
            Todo todo = ot.get();
            return this.likeRepository.findAllByTodo(todo).size();
        }
        return 0;
    }

    public void remove(LikeDto likeDto) {
        this.likeRepository.delete(likeDto.toEntity());
    }
}
