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
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    public CommentResponseDto create(Long schedule_id, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto, scheduleService.findScheduleById(schedule_id));

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }

    public CommentResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long scheduleId) {
        return scheduleService.findScheduleById(scheduleId).getComments().stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public void update(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = findCommentById(commentId);

        comment.updateComment(commentRequestDto);
    }

    public void delete(Long commentId) {
        commentRepository.delete(findCommentById(commentId));
    }

    public Comment findCommentById(Long scheduleId) {
        return commentRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }


}
