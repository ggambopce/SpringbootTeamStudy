package com.springboot.study.backend.domain.board.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.springboot.study.backend.domain.board.entity.Board;

import java.util.List;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JdbcBoardRepository implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 게시물 전체 조회
     * GET /api/board
     */
    @Override
    public List<Board> findAll() {
        String sql = """
                SELECT * FROM board
                """;

        return jdbcTemplate.query(sql, boardRowMapper());
    }

    /**
     * 게시물 단건 조회
     * GET /api/board/{post_id}
     */
    @Override
    public Board findById(Long post_id) {
        String sql = """
                SELECT * FROM board WHERE post_id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, boardRowMapper(), post_id);
        }
        catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 게시물 작성
     * POST /api/board
     */
    @Override
    public Board createBoard(Board board) {
        String sql = """
                INSERT INTO board (user_id, post_title, post_content)
                VALUES (?, ?, ?) 
                """;

        // post_id의 값이 Null일 경우 DB에서 자동 생성되며
        // 자동 생성된 post_id를 Response에 넘겨주기 위해 KeyHolder에 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // PreparedStatement를 사용하여 쿼리 실행
        // Statement.RETURN_GENERATED_KEYS 옵션을 사용하여 자동 생성된 키를 받도록 설정
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, board.getUser_id());
            ps.setString(2, board.getPost_title());
            ps.setString(3, board.getPost_content());
            return ps;
        }, keyHolder);
        
        // KeyHolder에서 자동 생성된 키를 Board 객체에 저장
        Long generatedId = keyHolder.getKey().longValue();
        board.setPost_id(generatedId);

        return board;
    }

    /**
     * 게시물 수정
     * PUT /api/board/{post_id}
     */
    @Override
    public Board editBoard(Board board) {
        // UPDATE 쿼리 작성
        String sql = """
                UPDATE board
                SET post_title = ?, post_content = ?, created_at = ?
                WHERE post_id = ?
                """;
    
        // JdbcTemplate의 update 메서드를 사용하여 쿼리 실행
        // 매개변수는 쿼리문의 '?'에 순서대로 매핑됩니다.
        jdbcTemplate.update(
            sql,
            board.getPost_title(),
            board.getPost_content(),
            LocalDateTime.now(), // 수정 시각을 현재 시간으로 업데이트
            board.getPost_id()   // WHERE 절에 사용될 post_id
        );

        // 수정된 Board 객체를 반환합니다.
        return board;
    }

    @Override
    public void deleteById(Long post_id) {
        // TODO Auto-generated method stub
        
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> new Board(
            rs.getLong("post_id"),
            rs.getLong("user_id"),
            rs.getString("post_title"),
            rs.getString("post_content"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}