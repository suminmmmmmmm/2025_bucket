package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.CategoryDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.service.CategoryService;
import com.example._2025_bucket.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CreateListController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryService categoryService;

    private static final String URL = "src/main/resources/static/images/";

    // 폼 페이지를 반환
    @GetMapping("/create")
    public String showCreateForm(TodoForm todoForm,
                                 Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "create-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(
            @Valid TodoForm todoForm,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "create-form";
        }
        try {
            MultipartFile bucketImage = todoForm.getFile();
            if (!bucketImage.isEmpty()) {
                // 외부 디렉토리에 저장 경로 설정
                Path filePath = Paths.get(URL + bucketImage.getOriginalFilename());

                // 파일 저장
                Files.write(filePath, bucketImage.getBytes());

                // 저장된 경로를 DTO에 설정
                TodoDto todoDto = TodoDto.builder()
                        .create_at(LocalDateTime.now())
                        .check_complete(false)
                        .image_path(bucketImage.getOriginalFilename())
                        .content(todoForm.getContent())
                        .goal_day(todoForm.getGoal_day())
                        .category(categoryService.getCategoryById(todoForm.getCategory()).toEntity())
                        .build();
                this.todoService.save(todoDto);
            }
        } catch (IOException e) {
            // 파일 입력 실패시 로직
        }
        return "redirect:/list"; // 저장 후 리스트 페이지로 리다이렉트
    }


//    @Controller
//    public class TodoListController {
//
//        private final TodoService todoService;
//
//        public TodoListController(TodoService todoService) {
//            this.todoService = todoService;
//        }
//
//        @GetMapping("/list")
//        public String showTodoList(Model model) {
//            List<TodoDto> todos = todoService.getTodoList();
//            model.addAttribute("todos", todos); // 뷰로 전달할 데이터
//            return "list"; // list.html 렌더링
//        }
//    }

}
