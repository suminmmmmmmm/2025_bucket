package com.example._2025_bucket.controller;

import com.example._2025_bucket.service.ReviewService;
import com.example._2025_bucket.service.TodoService;
import com.example._2025_bucket.dto.ReviewDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.form.ReviewForm;
import com.example._2025_bucket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@RequestMapping("/review")
@Controller
public class ReviewController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") long id,
                         @RequestParam String content,
                         Authentication authentication) {
        // id : 본글 id
        // content : 댓글
        System.out.printf(content);
        try{
            TodoDto todoDto = this.todoService.getTodo(id);
            System.out.println(todoDto.toString());
            this.reviewService.create(todoDto, content, authentication.getName());
        }catch (Exception e){

        }
        return "redirect:/list/detail/"+id;
    }

    @GetMapping("/modify/{id}")
    public String modifyReview(@PathVariable("id") long id,
                               Model model,
                               ReviewForm reviewForm,
                               Authentication authentication) {
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
                               BindingResult bindingResult,
                               Authentication authentication) {
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
    public String deleteReview(@PathVariable("id") long id,
                               Authentication authentication) {
        try{
            ReviewDto reviewDto = this.reviewService.getReview(id);
            System.out.println(reviewDto.toString());
            if(Objects.equals(authentication.getName(), this.reviewService.getReview(id).getUser().getUsername())){
                this.reviewService.delete(reviewDto);
            }
            return "redirect:/list/detail/"+reviewDto.getTodo().getId();
        }catch (Exception e){

        }
        // 돌아갈 곳이 없어서 임시로 홈페이지로 리디렉션
        return "redirect:/list";
    }

}
