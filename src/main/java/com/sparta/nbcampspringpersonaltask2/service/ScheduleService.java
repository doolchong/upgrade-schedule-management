package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto create(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);

        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(saveSchedule);
    }

    public ScheduleResponseDto getSchedule(Long scheduleId) {
        return new ScheduleResponseDto(findScheduleById(scheduleId));
    }

    @Transactional
    public void update(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.updateSchedule(scheduleRequestDto);
    }

    public Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }
}