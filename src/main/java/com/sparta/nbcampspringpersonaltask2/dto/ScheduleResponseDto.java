package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String weather;
    private final List<UserResponseDto> users;
    private final List<CommentResponseDto> comments;

    public static ScheduleResponseDto entityToDto(Schedule schedule, List<UserResponseDto> users) {
        return new ScheduleResponseDto(
                schedule.getUserId(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getWeather(),
                users,
                schedule.getComments().stream().map(CommentResponseDto::commentToDto).toList()
        );

    }
}
