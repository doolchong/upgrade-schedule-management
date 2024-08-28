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

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // USER -> USER
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UsersAndSchedules> usersAndSchedules;

    public User(String userName, String password, String email, UserRoleEnum role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void updateUser(UserRequestDto userRequestDto) {
        userName = userRequestDto.getUserName();
        email = userRequestDto.getEmail();
    }
}
