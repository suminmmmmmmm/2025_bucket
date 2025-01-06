package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute TodoDto todoDto,
                                   @RequestParam("bucketImage") MultipartFile bucketImage) {
        try {
            if (!bucketImage.isEmpty()) {
                // 외부 디렉토리에 저장 경로 설정
                String fileName = System.currentTimeMillis() + "_" + bucketImage.getOriginalFilename();
                String uploadDir = "C:/uploads/images/"; // 절대 경로 설정
                Path uploadPath = Paths.get(uploadDir);

                // 디렉토리가 없으면 생성
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 파일 저장
                Path filePath = uploadPath.resolve(fileName);
                bucketImage.transferTo(filePath.toFile());

                // 저장된 경로를 DTO에 설정
                todoDto.setImagePath("/images/" + fileName);
            }

            // 서비스 계층에 전달
            todoService.createTodo(todoDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/list"; // 저장 후 리스트 페이지로 리다이렉트
    }

    @GetMapping("/list")
    public String showTodoList(Model model) {
        List<TodoDto> todos = todoService.getTodoList();
        model.addAttribute("todos", todos); // 뷰로 전달할 데이터
        return "list"; // list.html 렌더링
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
