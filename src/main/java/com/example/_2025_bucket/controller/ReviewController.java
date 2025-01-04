package com.example._2025_bucket.controller;

import com.example._2025_bucket.Service.ReviewService;
import com.example._2025_bucket.Service.TodoService;
import com.example._2025_bucket.dto.ReviewDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.form.ReviewForm;
import com.example._2025_bucket.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/review")
@Controller
public class ReviewController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") long id,
                         @RequestParam String content) {
        // id : 본글 id
        // content : 댓글
        System.out.printf(content);
        try{
            TodoDto todoDto = this.todoService.getTodo(id);
            System.out.println(todoDto.toString());
            this.reviewService.create(todoDto, content);
        }catch (Exception e){

        }
        return "redirect:/list/detail/"+id;
    }

    @GetMapping("/modify/{id}")
    public String modifyReview(@PathVariable("id") long id,
                               Model model,
                               ReviewForm reviewForm) {
        // id : 댓글의 id
        ReviewDto reviewDto = this.reviewService.getReview(id);
        reviewForm.setContent(reviewDto.getContent());
        reviewForm.setId(reviewDto.getId());
        reviewForm.setTodoId(reviewDto.getTodo().getId());
        model.addAttribute("reviewForm", reviewForm);

        return "review_modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyReview(@PathVariable("id") long id,
                               @Valid ReviewForm reviewForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "review_modify";
        }
        try{
            ReviewDto reviewDto = this.reviewService.getReview(id);
            reviewDto.setContent(reviewForm.getContent());
            reviewDto.setModified_at(LocalDateTime.now());
            System.out.printf(reviewDto.toString());
            this.reviewService.update(reviewDto);

            return "redirect:/list/detail/"+ reviewDto.getTodo().getId();
        }catch (Exception e){

        }

        return "redirect:/list/detail/"+reviewForm.getTodoId();
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") long id) {
        try{
            ReviewDto reviewDto = this.reviewService.getReview(id);
            this.reviewService.delete(reviewDto);
            return "redirect:/list/detail/"+reviewDto.getTodo().getId();
        }catch (Exception e){

        }
        // 돌아갈 곳이 없어서 임시로 홈페이지로 리디렉션
        return "redirect:/";

    }

}
