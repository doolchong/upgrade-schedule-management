package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.entity.UsersAndSchedules;
import com.sparta.nbcampspringpersonaltask2.repository.UsersAndSchedulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersAndSchedulesService {

    private final UsersAndSchedulesRepository usersAndSchedulesRepository;
    private final UserService userService;

    @Transactional
    public void add(long userId, Schedule schedule) {
        UsersAndSchedules usersAndSchedules = new UsersAndSchedules(userService.findUserById(userId), schedule);

        usersAndSchedulesRepository.save(usersAndSchedules);
    }

    public List<UserResponseDto> getUserList(Schedule schedule) {
        List<User> list = usersAndSchedulesRepository.findAllBySchedule_ScheduleId(schedule.getScheduleId()).stream().map(UsersAndSchedules::getUser).toList();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : list) {
            UserResponseDto userResponseDto = UserResponseDto.entityToDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }
}
