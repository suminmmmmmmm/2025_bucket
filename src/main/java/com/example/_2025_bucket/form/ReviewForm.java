package com.example._2025_bucket.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewForm {
    @NotEmpty(message = "내용을 입력하세요")
    private String content;
}
