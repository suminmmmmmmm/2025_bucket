package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateListController {

    private final TodoService todoService;

    public CreateListController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 폼 페이지를 반환
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("todoDto", new TodoDto()); // 빈 TodoDto 객체를 전달
        return "create-form"; // create-form.html 페이지 렌더링
    }

    // 폼 데이터를 처리
    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute TodoDto todoDto) {
        todoService.createTodo(todoDto); // 서비스에 DTO 전달
        return "redirect:/list"; // 저장 후 리스트 페이지로 리다이렉트
    }
}
