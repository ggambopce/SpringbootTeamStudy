package com.springboot.study.backend.global.auth.interceptor;

import com.springboot.study.backend.global.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public JwtInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 쿠키에서 JWT 토큰 추출
        String token = extractTokenFromCookie(request);
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"인증 토큰이 필요합니다.\"}");
            response.setContentType("application/json");
            return false;
        }

        try {
            // JWT 토큰 검증 및 사용자명 추출
            String username = authService.getUsernameFromJwtToken(token);
            
            // request에 사용자 정보 저장
            request.setAttribute("username", username);
            
            return true;
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"유효하지 않은 토큰입니다.\"}");
            response.setContentType("application/json");
            return false;
        }
    }

    // 쿠키에서 JWT 토큰 추출
    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("auth_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}