package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.CategoryDto;
import com.example._2025_bucket.dto.UserDto;
import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.service.CategoryService;
import com.example._2025_bucket.service.TodoService;
import com.example._2025_bucket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import java.util.Objects;


@Controller
@RequestMapping("/list")
public class DetailController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    // 썸네일 저장 경로
    private static final String URL = "C:/uploads/images/";


    @GetMapping("")
    public String showTodoList(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories(); // 모든 카테고리 조회
        model.addAttribute("categories", categories);

        List<TodoDto> todos;
        if (categoryId != null) {
            todos = todoService.getTodosByCategory(categoryId); // 카테고리에 해당하는 TODO 조회
            model.addAttribute("selectedCategory", categoryId); // 선택된 카테고리 ID
        } else {
            todos = todoService.getAllTodos(); // 모든 TODO 조회
        }
        model.addAttribute("todos", todos);
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
            Model model,
            Authentication authentication) {
        try {
            TodoDto todoDto = todoService.getTodo(id);
            System.out.println(authentication.getName());
            System.out.println(todoDto.getUser().getEmail());
            if(!Objects.equals(authentication.getName(), todoDto.getUser().getEmail())){
                System.out.println("!!!!!!!!!!!!다른 사용자");
                return "redirect:/list/detail/" + id;

            }

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
        try{
            MultipartFile bucketImage = todoForm.getFile();
            TodoDto todoDto = this.todoService.getTodo(id);
            if (!bucketImage.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + bucketImage.getOriginalFilename();
                Path path = Paths.get(URL);

                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                Path filePath = path.resolve(fileName);
                bucketImage.transferTo(filePath.toFile());

                todoDto.setImage_path("/images/" + fileName);
            }

            todoDto.setContent(todoForm.getContent());
            todoDto.setGoal_day(todoForm.getGoal_day());
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
    public String delete(@PathVariable("id") long id,
                         Authentication authentication) {
        try{
            TodoDto todoDto = this.todoService.getTodo(id);
            long uid = todoDto.getUser().getId();
            if (Objects.equals(authentication.getName(), todoDto.getUser().getEmail())) {
                this.todoService.remove(todoDto);
                return "redirect:/list";
            }

        } catch (Exception e) {
            // 해당 글 없을 때 로직
        }
        return "redirect:/list/detail/" + id;
    }
}
