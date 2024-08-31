package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.SchedulesResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.UserRoleEnum;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final JwtUtil jwtUtil;
    private final ScheduleRepository scheduleRepository;
    private final UsersAndSchedulesService usersAndSchedulesService;
    private final WeatherService weatherService;

    @Transactional
    public ScheduleResponseDto create(HttpServletRequest servletRequest, ScheduleRequestDto scheduleRequestDto) {
        if (!isAdmin(jwtUtil.getTokenFromRequest(servletRequest))) {
            throw new SecurityException("권한이 없습니다.");
        }

        Schedule schedule = new Schedule(scheduleRequestDto);
        schedule.setWeather(weatherService.getCallWeather(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd"))));

        Schedule saveSchedule = scheduleRepository.save(schedule);

        usersAndSchedulesService.add(scheduleRequestDto.getUserId(), saveSchedule);

        return ScheduleResponseDto.entityToDto(saveSchedule, usersAndSchedulesService.getUserList(findScheduleById(scheduleRequestDto.getUserId())));
    }

    public ScheduleResponseDto getSchedule(long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);

        return ScheduleResponseDto.entityToDto(schedule, usersAndSchedulesService.getUserList(findScheduleById(scheduleId)));
    }

    public Page<SchedulesResponseDto> getScheduleList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());

        return scheduleRepository.findAll(pageable).map(SchedulesResponseDto::entityToDto);
    }

    @Transactional
    public void update(long scheduleId, ScheduleRequestDto scheduleRequestDto, HttpServletRequest servletRequest) {
        if (!isAdmin(jwtUtil.getTokenFromRequest(servletRequest))) {
            throw new SecurityException("권한이 없습니다.");
        }

        Schedule schedule = findScheduleById(scheduleId);

        schedule.updateSchedule(scheduleRequestDto);
    }

    @Transactional
    public void delete(long scheduleId) {
        scheduleRepository.delete(findScheduleById(scheduleId));
    }

    @Transactional
    public void addUserToSchedule(long scheduleId, long userId) {
        usersAndSchedulesService.add(userId, findScheduleById(scheduleId));
    }

    public Schedule findScheduleById(long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }

    private boolean isAdmin(String token) {
        token = jwtUtil.substringToken(token);
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        String role = claims.get("auth", String.class);

        if (UserRoleEnum.ADMIN.getAuthority().equals(role)) {
            return true;
        }
        return false;
    }
}