package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.UsersAndSchedules;
import com.sparta.nbcampspringpersonaltask2.repository.UsersAndSchedulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersAndSchedulesService {

    private final UsersAndSchedulesRepository usersAndSchedulesRepository;
    private final UserService userService;

    public void add(Long userId, Schedule schedule) {
        UsersAndSchedules usersAndSchedules = new UsersAndSchedules(userService.findUserById(userId), schedule);

        usersAndSchedulesRepository.save(usersAndSchedules);
    }
}
