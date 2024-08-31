package com.sparta.nbcampspringpersonaltask2.controller;

import com.sparta.nbcampspringpersonaltask2.dto.JwtTokenResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.LoginRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/sign-up")
    public ResponseEntity<JwtTokenResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.create(userRequestDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserList());
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> update(@PathVariable long userId, @RequestBody UserRequestDto userRequestDto) {
        userService.update(userId, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @DeleteMapping("/user/{userId}")
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }

    @PostMapping("/user/login")
    public ResponseEntity<JwtTokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto, response, request));
    }

}
