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
    public ResponseEntity<CommentResponseDto> create(@PathVariable long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.create(scheduleId, commentRequestDto));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

    @GetMapping("/{scheduleId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable long scheduleId) {
        List<CommentResponseDto> comments = commentService.getCommentList(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.update(commentId, commentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable long commentId) {
        commentService.delete(commentId);
    }

}
