package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.ScheduleRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.ScheduleResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.SchedulesResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.UserRoleEnum;
import com.sparta.nbcampspringpersonaltask2.jwt.JwtUtil;
import com.sparta.nbcampspringpersonaltask2.repository.ScheduleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final JwtUtil jwtUtil;
    private final ScheduleRepository scheduleRepository;
    private final UsersAndSchedulesService usersAndSchedulesService;

    public ScheduleResponseDto create(HttpServletRequest servletRequest, ScheduleRequestDto scheduleRequestDto) {
        Cookie[] list = servletRequest.getCookies();

        if (!isAdmin(list[0].getValue())) {
            throw new SecurityException(list[0].getValue());
        }

        Schedule schedule = new Schedule(scheduleRequestDto);

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
        Pageable pageable = PageRequest.of(page, size);

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

        if (token != null && token.startsWith("Bearer%")) {
            token = token.substring(9);
            Claims claims = jwtUtil.getUserInfoFromToken(token);

            String role = claims.get("auth", String.class);

            if ("ADMIN".equals(role)) {
                return true;
            }
        }
        return false;
    }
}