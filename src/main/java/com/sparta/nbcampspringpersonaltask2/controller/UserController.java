package com.sparta.nbcampspringpersonaltask2.controller;

import com.sparta.nbcampspringpersonaltask2.dto.UserRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.service.UserService;
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

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.create(userRequestDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        userService.update(userId, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @DeleteMapping("/user/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
