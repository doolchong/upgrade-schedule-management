package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long userId;
    private final String userName;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        userName = user.getUserName();
        email = user.getEmail();
        password = user.getPassword();
        createdAt = user.getCreatedAt();
        modifiedAt = user.getModifiedAt();
    }

}
