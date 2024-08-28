package com.sparta.nbcampspringpersonaltask2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nbcampspringpersonaltask2.dto.ExceptionResponseDto;
import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.jwt.JwtUtil;
import com.sparta.nbcampspringpersonaltask2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/user/login") || url.startsWith("/api/user/sign-up"))
        ) {
            log.info("인증 처리를 하지 않는 URL : " + url);
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    tokenErrorExceptionHandler(response, "토큰이 유효하지 않습니다.", url);
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                Optional<User> user = userRepository.findByUserName(info.getSubject());

                if (user.isEmpty()) {
                    notFoundTokenExceptionHandler(response, "사용자를 찾을 수 없습니다.", url);
                }
                
                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                notFoundTokenExceptionHandler(response, "토큰이 존재하지 않습니다.", url);
            }
        }
    }

    private void notFoundTokenExceptionHandler(ServletResponse response, String message, String url) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, message, URI.create(url));
        try {
            String json = new ObjectMapper().writeValueAsString(exceptionResponseDto);
            httpServletResponse.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void tokenErrorExceptionHandler(ServletResponse response, String message, String url) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(HttpStatus.UNAUTHORIZED, message, URI.create(url));
        try {
            String json = new ObjectMapper().writeValueAsString(exceptionResponseDto);
            httpServletResponse.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}