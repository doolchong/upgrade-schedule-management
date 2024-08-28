package com.sparta.nbcampspringpersonaltask2.exceptionhandler;

import com.sparta.nbcampspringpersonaltask2.dto.ExceptionResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ExceptionResponseDto> handleSecurityException(SecurityException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.FORBIDDEN, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponseDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponseDto> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleMalformedJwtException(MalformedJwtException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponseDto> handleSignatureException(SignatureException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDto);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDto);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnsupportedJwtException(UnsupportedJwtException e, HttpServletRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), URI.create(request.getRequestURI()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }
}
