package com.sparta.nbcampspringpersonaltask2.entity;

import com.sparta.nbcampspringpersonaltask2.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    public User(UserRequestDto userRequestDto) {
        userName = userRequestDto.getUserName();
        email = userRequestDto.getEmail();
    }

    public void updateUser(UserRequestDto userRequestDto) {
        userName = userRequestDto.getUserName();
        email = userRequestDto.getEmail();
    }
}
