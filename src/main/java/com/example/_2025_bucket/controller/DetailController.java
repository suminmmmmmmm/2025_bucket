package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.CategoryDto;
import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.service.CategoryService;
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
import java.util.List;


@Controller
@RequestMapping("/list")
public class DetailController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryService categoryService;
    //@Autowired
    //private UserDetailsService userDetailsService;

    // 썸네일 저장 경로
    private static final String URL = "src/main/resources/static/images/";


    @GetMapping("")
    public String showTodoList(Model model) {
        List<TodoDto> todos = this.todoService.getAllTodos();
        model.addAttribute("todos", todos); // 뷰로 전달할 데이터
        return "list"; // list.html 렌더링
    }

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
            List<CategoryDto> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories); // 모든 카테고리를 전달
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
        Path filePath = Paths.get(URL + todoForm.getFile().getOriginalFilename());
        try{
            Files.write(filePath, file.getBytes());

            TodoDto todoDto = this.todoService.getTodo(id);

            System.out.printf("불러온 TODO id : " + todoDto.getId());
            System.out.printf(todoDto.toString());

            todoDto.setContent(todoForm.getContent());
            todoDto.setGoal_day(todoForm.getGoal_day());
            todoDto.setImage_path(todoForm.getFile().getOriginalFilename());
            todoDto.setCategory(this.categoryService.getCategoryById(todoForm.getCategory()).toEntity());

            todoDto.setModified_at(LocalDateTime.now());

            System.out.printf(todoDto.toString());

            this.todoService.update(todoDto);
        }catch (Exception e) {

            System.out.println("문제발생");

        }

        return "redirect:/list/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        try{
            TodoDto todoDto = this.todoService.getTodo(id);
            this.todoService.remove(todoDto);
        } catch (Exception e) {
            // 해당 글 없을 때 로직
        }
        return "redirect:/list";
    }
}
