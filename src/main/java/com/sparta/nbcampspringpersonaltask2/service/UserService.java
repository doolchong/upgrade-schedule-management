package com.sparta.nbcampspringpersonaltask2.service;

import com.sparta.nbcampspringpersonaltask2.config.PasswordEncoder;
import com.sparta.nbcampspringpersonaltask2.dto.JwtTokenResponseDto;
import com.sparta.nbcampspringpersonaltask2.dto.LoginRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserRequestDto;
import com.sparta.nbcampspringpersonaltask2.dto.UserResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.entity.UserRoleEnum;
import com.sparta.nbcampspringpersonaltask2.jwt.JwtUtil;
import com.sparta.nbcampspringpersonaltask2.repository.UserRepository;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public JwtTokenResponseDto create(UserRequestDto userRequestDto) {
        String userName = userRequestDto.getUserName();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUserName(userName);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = userRequestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userName, password, email, role);

        User saveUser = userRepository.save(user);

        return new JwtTokenResponseDto(saveUser.getPassword());
    }

    public JwtTokenResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response, HttpServletRequest request) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new SignatureException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new SignatureException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUserName(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);

        return new JwtTokenResponseDto(token);
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
