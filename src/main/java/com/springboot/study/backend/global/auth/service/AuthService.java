package com.springboot.study.backend.global.auth.service;

import com.springboot.study.backend.domain.user.entity.User;
import com.springboot.study.backend.domain.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.Optional;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final String jwtSecret = "yRDz7h8Z7uYWh4p3Mq03WIxpFUN0m7VylfglY4PlhELCMuSoXzdeS3qbkCFeMjNl";
  private final int jwtExpirationMs = 24 * 60 * 60 * 1000; // 24시간 

  public AuthService(UserRepository userRepository){
    this.userRepository = userRepository;
  }
  // 회원가입
  public User register(String username, String password) {
    // 중복 사용자 확인
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new RuntimeException("이미 존재하는 사용자명입니다: " + username);
    }

    // 새 사용자 생성 및 저장
    User newUser = new User(null, username, password);
    return userRepository.save(newUser);
  }

  // 로그인
  public User login(String username, String password) {
    // 사용자 조회
    Optional<User> findUser = userRepository.findByUsername(username);
    if (findUser.isEmpty()) {
      throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    
    User user = findUser.get();
    
    // 비밀번호 검증
    if (!password.equals(user.getPassword())) {
      throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    
    return user;
  }

  // JWT 토큰 생성
  public String generateJwtToken(User user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
        .compact();
  }

  // JWT 토큰 검증 및 사용자명 추출
  public String getUsernameFromJwtToken(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    } catch (Exception e) {
      throw new RuntimeException("유효하지 않은 JWT 토큰입니다: " + e.getMessage());
    }
  }

  // JWT 토큰 유효성 검증
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
