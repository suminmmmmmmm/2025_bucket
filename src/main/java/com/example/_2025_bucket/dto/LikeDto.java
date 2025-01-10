package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Likes;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LikeDto {
    private long id;
    private TodoDto todo;
    private UserDto user;

    public Likes toEntity(){
        return Likes.builder()
                .id(this.id)
                .todo(this.todo.toEntity())
                .user(this.user.toEntity())
                .build();
    }
}
