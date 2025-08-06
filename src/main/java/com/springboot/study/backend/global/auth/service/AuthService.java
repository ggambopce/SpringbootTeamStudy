package global.auth.service;

import domain.user.entity.User;
import domain.user.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;

  public AuthService(UserRepository userRepository){
    this.userRepository = userRepository;
  }
  public User login(String username, String password) {
    User user;
    // 사용자 조회
    try{
      user = userRepository.findByUsername(username);
    } catch (Exception e) {
      throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    // 비밀번호 검증
    if (password != user.getPassword()) {
      throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    // 로그인 성공 응답
    return user;
  }
}
