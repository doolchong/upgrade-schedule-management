package com.sparta.nbcampspringpersonaltask2.dto;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class SchedulesResponseDto {

    private final String writerName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int numberOfComments;

    public SchedulesResponseDto(Schedule schedule) {
        writerName = schedule.getWriterName();
        scheduleTitle = schedule.getScheduleTitle();
        scheduleContent = schedule.getScheduleContent();
        createdAt = schedule.getCreatedAt();
        modifiedAt = schedule.getModifiedAt();
        numberOfComments = schedule.getComments().size();
    }
}
