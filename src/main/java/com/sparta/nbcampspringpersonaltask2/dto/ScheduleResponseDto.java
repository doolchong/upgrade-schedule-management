package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {

    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentResponseDto> comments;

    public ScheduleResponseDto(Schedule schedule) {
        this.userId = schedule.getUserId();
        this.scheduleTitle = schedule.getScheduleTitle();
        this.scheduleContent = schedule.getScheduleContent();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        comments = schedule.getComments().stream().map(CommentResponseDto::new).toList();
    }
}
