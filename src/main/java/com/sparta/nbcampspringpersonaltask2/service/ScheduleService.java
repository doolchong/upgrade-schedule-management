package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.SchedulesResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.jwt.JwtUtil;
import com.sparta.nbcampspringpersonaltask2.repository.ScheduleRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final JwtUtil jwtUtil;
    private final ScheduleRepository scheduleRepository;
    private final UsersAndSchedulesService usersAndSchedulesService;
    private final WeatherService weatherService;

    public ScheduleResponseDto create(HttpServletRequest servletRequest, ScheduleRequestDto scheduleRequestDto) {
        if (!isAdmin(jwtUtil.getTokenFromRequest(servletRequest))) {
            throw new SecurityException("권한이 없습니다.");
        }

        Schedule schedule = new Schedule(scheduleRequestDto);
        schedule.setWeather(weatherService.getCallWeather(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"))));

        Schedule saveSchedule = scheduleRepository.save(schedule);

        usersAndSchedulesService.add(scheduleRequestDto.getUserId(), saveSchedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);
        scheduleResponseDto.setUsers(usersAndSchedulesService.getUsers(findScheduleById(scheduleRequestDto.getUserId())));

        return scheduleResponseDto;
    }

    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        scheduleResponseDto.setUsers(usersAndSchedulesService.getUsers(findScheduleById(scheduleId)));

        return scheduleResponseDto;
    }

    public Page<SchedulesResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());

        return scheduleRepository.findAll(pageable).map(SchedulesResponseDto::new);
    }

    @Transactional
    public void update(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.updateSchedule(scheduleRequestDto);
    }

    public void delete(Long scheduleId) {
        scheduleRepository.delete(findScheduleById(scheduleId));
    }

    public void addUserToSchedule(Long scheduleId, Long userId) {
        usersAndSchedulesService.add(userId, findScheduleById(scheduleId));
    }

    public Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }

    private boolean isAdmin(String token) {
        token = jwtUtil.substringToken(token);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        String role = claims.get("auth", String.class);

        if ("ADMIN".equals(role)) {
            return true;
        }
        return false;
    }
}