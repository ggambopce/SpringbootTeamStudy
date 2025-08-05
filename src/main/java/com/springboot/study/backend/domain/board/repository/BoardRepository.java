package com.springboot.study.backend.domain.board.repository;

import com.springboot.study.backend.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository를 상속받았기 때문에 
    // findAll(), findById(), save(), delete() 등의
    // CRUD 메서드가 자동으로 생성됩니다.
}