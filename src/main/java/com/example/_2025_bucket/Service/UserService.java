package com.example._2025_bucket.service;


import com.example._2025_bucket.dto.UserDto;
import com.example._2025_bucket.entity.User;
import com.example._2025_bucket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 일반적인 User 테이블과 연관된 비즈니스 로직 처리
 * - 회원가입
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void create(UserDto userDto) {
        //비밀번호와 일치하는 지 검증
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 일치하지 않습니다.");
        }
        // 이메일 중복 확인
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // insert, update
        userRepository.save(User.builder()
                .email(userDto.getEmail())
                // 비번 암호화!! -> DI
                .password( bCryptPasswordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .create_at(LocalDateTime.now())
                .build());


    }
}
