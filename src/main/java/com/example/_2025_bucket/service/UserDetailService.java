package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.UserDto;
import com.example._2025_bucket.entity.User;
import com.example._2025_bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException(email));
    }

    public void save(UserDto userDto) {
        userRepository.save(userDto.toEntity());
    }
}
