package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.LikeDto;
import com.example._2025_bucket.service.LikeService;
import com.example._2025_bucket.service.TodoService;
import com.example._2025_bucket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String like(@PathVariable("id") Long id,
                       Authentication authentication) {
        try {
            LikeDto likeDto = this.likeService.getLikeByTodoAndUser(
                    this.todoService.getTodo(id).toEntity(),
                    this.userService.getUserByEmail(authentication.getName()).toEntity()
            );
            if(likeDto == null) {
                this.likeService.create(LikeDto.builder()
                        .todo(this.todoService.getTodo(id).toEntity())
                        .user(this.userService.getUserByEmail(authentication.getName()).toEntity())
                        .build());
            }
            else{
                this.likeService.remove(likeDto.toEntity());
            }
        }catch (Exception e){

        }
        return "redirect:/list/detail/" + id;
    }
}
