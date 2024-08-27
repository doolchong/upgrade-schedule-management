package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final String jwtToken;

    public SignUpResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
