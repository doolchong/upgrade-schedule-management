package com.sparta.nbcampspringpersonaltask2.repository;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import com.sparta.nbcampspringpersonaltask2.entity.UsersAndSchedules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersAndSchedulesRepository extends JpaRepository<UsersAndSchedules, Long> {

    Page<UsersAndSchedules> findAll(Pageable pageable);

    List<UsersAndSchedules> findAllBySchedule(Schedule schedule);
}
