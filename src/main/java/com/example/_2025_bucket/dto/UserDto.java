package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
public class UserDto {
    private long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime create_at;

    public User toEntity(){
        return User.builder()
                .create_at(this.create_at)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .id(this.id)
                .build();
    }
}
