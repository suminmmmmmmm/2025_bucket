package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.UserDto;
import com.example._2025_bucket.service.UserDetailService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserDetailService userDetailService;

    @GetMapping("/")
    public String index(){
        System.out.println("메인화면");
        return "index";
    }
}
