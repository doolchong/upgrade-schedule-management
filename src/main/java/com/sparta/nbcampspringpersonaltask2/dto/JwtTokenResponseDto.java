package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;

@Getter
public class JwtTokenResponseDto {

    private final String jwtToken;

    public JwtTokenResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
