package com.springboot.study.backend.global.auth.service;

import com.springboot.study.backend.domain.user.entity.User;
import com.springboot.study.backend.domain.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

  private final UserRepository userRepository;

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
}
