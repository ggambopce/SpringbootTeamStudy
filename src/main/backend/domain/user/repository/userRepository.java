package domain.user.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import domain.user.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public userRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

  // username으로 사용자 조회
  public User findByUsername(String username) {
    String sql = "SELECT id, username, password FROM users WHERE username = ? LIMIT 1";
    User user = (User) jdbcTemplate.query(sql, new UserRowMapper(), username);
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
