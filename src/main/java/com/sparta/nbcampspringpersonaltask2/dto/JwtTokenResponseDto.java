package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenResponseDto {

    private final String jwtToken;

    public static JwtTokenResponseDto tokenToDto(String jwtToken) {
        return new JwtTokenResponseDto(jwtToken);
    }
}
