package com.example._2025_bucket.controller;

import com.example._2025_bucket.Service.TodoService;
import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.dto.TodoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/list")
public class DetailController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserDetailsService userDetailsService;

    //상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
        System.out.printf("상세보기 "+ id + "요청됨");

        try {
            TodoDto todoDto = todoService.getTodo(id);
            System.out.println(todoDto.toString());
            model.addAttribute("todoDto", todoDto); // 아이디값 화면에 전달
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "detail";
    }

    //수정
    @GetMapping("/modify/{id}")
    public String modify(
            TodoForm todoForm,
            @PathVariable("id") long id,
            Model model) {
        try {
            TodoDto todoDto = todoService.getTodo(id);
            System.out.println(todoDto.toString() + "GET");
            todoForm.setContent(todoDto.getContent());

        }
        catch (Exception e) {

        }
        System.out.printf("modify에 들어간 내용: "+todoForm.getContent());
        return "modify";
    }

    //수정결과 받아오기
    @PostMapping("/modify/{id}")
    public String modify(
            @Valid TodoForm todoForm,
            BindingResult bindingResult,
            @PathVariable("id") long id) {
        if(bindingResult.hasErrors()) {
            return "modify";
        }
        this.todoService.update(TodoDto.builder()
                        .content(todoForm.getContent())
                        .goal_day(todoForm.getGoal_day())
                        .modified_at(LocalDateTime.now())
                        .id(id)
                        //.user(userDetailsService.loadUserByUsername( SecurityContextHolder.getContext().getAuthentication().getName()))
                        // 나중에 인증 / 세션 구현한 후 추가
                        .build());
        return "redirect:/list/detail/" + id;
    }
}
