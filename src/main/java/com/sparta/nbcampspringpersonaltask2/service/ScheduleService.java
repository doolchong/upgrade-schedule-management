package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.SchedulesResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Page<SchedulesResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        return schedules.map(SchedulesResponseDto::new);
    }

    @Transactional
    public void update(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.updateSchedule(scheduleRequestDto);
    }

    public void delete(Long scheduleId) {
        scheduleRepository.delete(findScheduleById(scheduleId));
    }

    public Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }
}