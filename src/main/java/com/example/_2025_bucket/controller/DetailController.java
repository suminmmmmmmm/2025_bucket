package com.example._2025_bucket.controller;

import com.example._2025_bucket.Service.TodoService;
import com.example._2025_bucket.dto.TodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class DetailController {
    @Autowired
    private TodoService todoService;

    @GetMapping("list/detail/{id}")
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
}
