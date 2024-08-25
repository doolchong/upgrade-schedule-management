package com.sparta.nbcampspringpersonaltask2.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long userId;
    private String scheduleTitle;
    private String scheduleContent;
}
