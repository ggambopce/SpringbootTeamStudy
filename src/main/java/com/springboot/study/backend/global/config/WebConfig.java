package com.springboot.study.backend.global.config;

import com.springboot.study.backend.global.auth.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
  1. 클라이언트 HTTP 요청
           ↓
  2. DispatcherServlet이 요청 받음
           ↓
  3. WebConfig에서 등록된 인터셉터 확인
           ↓
  4. JwtInterceptor.preHandle() 실행
     - 경로 체크 (제외 경로 체크)
     - JWT 토큰 추출 및 검증
     - request.setAttribute("username", username)
           ↓
  5. 컨트롤러 메서드 실행
           ↓
  6. 응답 반환
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")           // 모든 경로에 적용
                .excludePathPatterns(                         // 제외 경로
                    "/api/auth/**",
                    "/error",
                    "/favicon.ico",
                    "/static/**",
                    "/css/**", 
                    "/js/**", 
                    "/images/**"
                );
    }
}