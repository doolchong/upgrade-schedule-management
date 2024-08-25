package com.sparta.nbcampspringpersonaltask2.repository;

import com.sparta.nbcampspringpersonaltask2.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAll(Pageable pageable);
}
