package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final String comment;
    private final String writerName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static CommentResponseDto commentToDto(Comment comment) {
        return new CommentResponseDto(
                comment.getComment(),
                comment.getWriterName(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
