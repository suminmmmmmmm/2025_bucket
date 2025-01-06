package com.example._2025_bucket.controller;

import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/list")

public class DetailController {
    @Autowired
    private TodoService todoService;
    //@Autowired
    //private UserDetailsService userDetailsService;

    // 썸네일 저장 경로
    private static final String URL = "src/main/resources/static/uploads/";

    //상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
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
            todoForm.setContent(todoDto.getContent());

        }
        catch (Exception e) {
            //버킷리스트가 없을때 로직
        }
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
        MultipartFile file = todoForm.getFile();
        Path filaPath = Paths.get(URL + todoForm.getFile().getOriginalFilename());
        System.out.println("\n업로드 파일 경로: " + (filaPath.toString()));
        try{
            Files.write(filaPath, file.getBytes());
            TodoDto todoDto = this.todoService.getTodo(id);
            System.out.println("조회된 TOOD: " + todoDto.toString());
            todoDto.setContent(todoForm.getContent());
            todoDto.setGoal_day(todoForm.getGoal_day());
            todoDto.setImage_path(todoForm.getFile().getOriginalFilename());
            System.out.println(file.getOriginalFilename());
            todoDto.setModified_at(LocalDateTime.now());

            System.out.printf(todoDto.toString());

            this.todoService.update(todoDto);
        }catch (Exception e) {
            // 파일 입력 실패시 로직
            System.out.println("파일저장실패");
        }

        return "redirect:/list/detail/" + id;
    }
}
