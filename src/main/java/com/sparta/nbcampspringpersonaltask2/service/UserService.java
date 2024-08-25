package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.dto.UserRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto);

        User saveUser = userRepository.save(user);

        return new UserResponseDto(saveUser);
    }

    public UserResponseDto getUser(Long userId) {
        return new UserResponseDto(findUserById(userId));
    }

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다.")
        );
    }

    @Transactional
    public void update(Long userId, UserRequestDto userRequestDto) {
        User user = findUserById(userId);

        user.updateUser(userRequestDto);
    }

    public void delete(Long userId) {
        userRepository.delete(findUserById(userId));
    }
}
