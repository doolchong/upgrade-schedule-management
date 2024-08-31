package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.CommentRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.CommentResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Comment;
import com.sparta.nbcampspringpersonaltask2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    @Transactional
    public CommentResponseDto create(long schedule_id, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto, scheduleService.findScheduleById(schedule_id));

        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.commentToDto(saveComment);
    }

    public CommentResponseDto getComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );
        return CommentResponseDto.commentToDto(comment);
    }

    public List<CommentResponseDto> getCommentList(long scheduleId) {
        return scheduleService.findScheduleById(scheduleId).getComments().stream().map(CommentResponseDto::commentToDto).toList();
    }

    @Transactional
    public void update(long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = findCommentById(commentId);

        comment.updateComment(commentRequestDto);
    }

    @Transactional
    public void delete(long commentId) {
        commentRepository.delete(findCommentById(commentId));
    }

    public Comment findCommentById(long scheduleId) {
        return commentRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}
