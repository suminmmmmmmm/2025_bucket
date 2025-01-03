package com.example._2025_bucket.controller;

import com.example._2025_bucket.Service.ReviewService;
import com.example._2025_bucket.Service.TodoService;
import com.example._2025_bucket.dto.ReviewDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.form.ReviewForm;
import com.example._2025_bucket.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
                               ReviewForm reviewForm){
        ReviewDto reviewDto = this.reviewService.getReview(id);
        reviewDto.setContent(reviewForm.getContent());
        return "review_modify";
    }

}
