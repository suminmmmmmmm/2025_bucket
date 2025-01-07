package com.example._2025_bucket.controller;


import com.example._2025_bucket.service.TodoService;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import com.example._2025_bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;

    @GetMapping("/user")
    public String userInfo(@AuthenticationPrincipal User user) {
        long id = user.getId();
        return "redirect:/user/{id}";
    }

    @GetMapping("/user/{id}")
    public String getTodosForUser(Model model, @AuthenticationPrincipal User user) {
        List<Todo> todos = todoService.getTodosByUserId(user.getId());
        model.addAttribute("todos", todos);
        return "todo"; // 뷰 템플릿
    }
}
