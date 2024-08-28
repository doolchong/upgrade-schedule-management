package com.sparta.nbcampspringpersonaltask2.controller;

import com.sparta.nbcampspringpersonaltask2.dto.CommentRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.CommentResponseDto;
import com.sparta.nbcampspringpersonaltask2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<CommentResponseDto> create(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.create(scheduleId, commentRequestDto));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

    @GetMapping("/{scheduleId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getComments(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.update(commentId, commentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

}
