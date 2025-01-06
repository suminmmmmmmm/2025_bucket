package com.example._2025_bucket.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class TodoForm {
    @NotEmpty(message = "내용을 입력하세요")
    private String content;
    @NotNull(message = "날짜를 선택하세요")
    private LocalDate goal_day;

    private MultipartFile file;
}
