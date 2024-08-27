package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScheduleResponseDto {

    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private List<UserResponseDto> users;
    private final List<CommentResponseDto> comments;

    public ScheduleResponseDto(Schedule schedule) {
        userId = schedule.getUserId();
        scheduleTitle = schedule.getScheduleTitle();
        scheduleContent = schedule.getScheduleContent();
        createdAt = schedule.getCreatedAt();
        modifiedAt = schedule.getModifiedAt();
        comments = schedule.getComments().stream().map(CommentResponseDto::new).toList();
    }
}
