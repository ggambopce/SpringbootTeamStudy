package com.springboot.study.backend.domain.user.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import com.springboot.study.backend.domain.user.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public UserRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

  // username으로 사용자 조회
  public Optional<User> findByUsername(String username) {
    String sql = "SELECT id, username, password FROM users WHERE username = ?";
    List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
    return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
  }

  // 사용자 저장 (회원가입)
  public User save(User user) {
    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    return user;
  }

  private static class UserRowMapper implements RowMapper<User> {
    @Override
   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
          rs.getLong("id"),
          rs.getString("username"),
          rs.getString("password")
        );
      }
  }
  
}
