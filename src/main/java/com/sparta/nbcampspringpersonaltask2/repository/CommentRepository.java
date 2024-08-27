package com.sparta.nbcampspringpersonaltask2.repository;

import com.sparta.nbcampspringpersonaltask2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
