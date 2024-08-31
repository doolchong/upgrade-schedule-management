package com.sparta.nbcampspringpersonaltask2.filter;

import com.sparta.nbcampspringpersonaltask2.entity.User;
import com.sparta.nbcampspringpersonaltask2.jwt.JwtUtil;
import com.sparta.nbcampspringpersonaltask2.repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
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
                try {
                    Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(token);
                } catch (SecurityException | MalformedJwtException | SignatureException e) {
                    JwtUtil.logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명 입니다.");
                    return;
                } catch (ExpiredJwtException e) {
                    JwtUtil.logger.error("Expired JWT token, 만료된 JWT token 입니다.");
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT token 입니다.");
                    return;
                } catch (UnsupportedJwtException e) {
                    JwtUtil.logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰 입니다.");
                    return;
                } catch (IllegalArgumentException e) {
                    JwtUtil.logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰 입니다.");
                    return;
                } catch (Exception e) {
                    JwtUtil.logger.error("JWT 토큰 검증 중 오류가 발생했습니다.", e);
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰 검증 중 오류가 발생했습니다.");
                    return;
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                Optional<User> user = userRepository.findByUserName(info.getSubject());

                if (user.isEmpty()) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "사용자를 찾을 수 없습니다.");
                }

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 존재하지 않습니다.");
            }
        }
    }
}