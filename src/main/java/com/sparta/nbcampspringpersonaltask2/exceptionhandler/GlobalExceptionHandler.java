package com.sparta.nbcampspringpersonaltask2.exceptionhandler;

import com.sparta.nbcampspringpersonaltask2.dto.ExceptionResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ExceptionResponseDto> handleSecurityException(SecurityException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.FORBIDDEN, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponseDto> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleMalformedJwtException(MalformedJwtException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponseDto> handleSignatureException(SignatureException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.UNAUTHORIZED, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.UNAUTHORIZED, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnsupportedJwtException(UnsupportedJwtException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponseDto.makeExceptionDto(
                HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI())
        ));
    }
}
