package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.entity.UsersAndSchedules;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SchedulesResponseDto {

    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long comments;

    public SchedulesResponseDto(Schedule schedule) {
        userId = schedule.getUserId();
        scheduleTitle = schedule.getScheduleTitle();
        scheduleContent = schedule.getScheduleContent();
        createdAt = schedule.getCreatedAt();
        modifiedAt = schedule.getModifiedAt();
        comments = (long) schedule.getComments().size();
    }
}
