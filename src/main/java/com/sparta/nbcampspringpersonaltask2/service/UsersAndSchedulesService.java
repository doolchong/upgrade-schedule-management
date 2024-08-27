package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.entity.UsersAndSchedules;
import com.sparta.nbcampspringpersonaltask2.repository.UsersAndSchedulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersAndSchedulesService {

    private final UsersAndSchedulesRepository usersAndSchedulesRepository;
    private final UserService userService;

    public void add(Long userId, Schedule schedule) {
        UsersAndSchedules usersAndSchedules = new UsersAndSchedules(userService.findUserById(userId), schedule);

        usersAndSchedulesRepository.save(usersAndSchedules);
    }

    public List<UserResponseDto> getUsers(Schedule schedule) {
        List<User> list = usersAndSchedulesRepository.findAllBySchedule(schedule).stream().map(UsersAndSchedules::getUser).toList();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : list) {
            UserResponseDto userResponseDto = new UserResponseDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }
}
