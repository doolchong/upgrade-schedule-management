package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponseDto {

    private final String timestamp = String.valueOf(LocalDateTime.now());
    private final HttpStatus status;
    private final String error;
    private final URI path;
}
