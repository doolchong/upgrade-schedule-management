package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String userName;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
