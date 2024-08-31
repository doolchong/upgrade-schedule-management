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
@RequiredArgsConstructor
public class SchedulesResponseDto {

    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int comments;

    public static SchedulesResponseDto entityToDto(Schedule schedule) {
        return new SchedulesResponseDto(
                schedule.getUserId(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getComments().size()
        );
    }
}
