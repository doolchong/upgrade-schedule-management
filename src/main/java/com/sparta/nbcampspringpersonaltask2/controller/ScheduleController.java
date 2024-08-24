package com.sparta.nbcampspringpersonaltask2.controller;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.create(scheduleRequestDto));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedule(scheduleId));
    }

    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.update(scheduleId, scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedule(scheduleId)); // 업데이트 후 수정일은 바로 적용이 안되길래 getSchedule로 가져옴
    }
}
