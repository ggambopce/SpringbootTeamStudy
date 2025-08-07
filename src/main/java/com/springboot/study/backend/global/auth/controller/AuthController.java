package com.springboot.study.backend.domain.user.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.springboot.study.backend.domain.user.entity.User;
import com.springboot.study.backend.global.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            User user = authService.login(request.getUsername(), request.getPassword());
            String jwtToken = authService.generateJwtToken(user);
            
            // HttpOnly 쿠키 설정
            Cookie cookie = new Cookie("auth_token", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // http 쿠키전송 허용
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 24시간
            response.addCookie(cookie);
            
            return ResponseEntity.ok("로그인 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DTO 임시
    public static class RegisterRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}