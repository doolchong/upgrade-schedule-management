package com.sparta.nbcampspringpersonaltask2.repository;

import com.sparta.nbcampspringpersonaltask2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
