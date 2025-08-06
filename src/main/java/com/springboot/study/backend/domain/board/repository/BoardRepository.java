package com.springboot.study.backend.domain.board.repository;

import org.springframework.stereotype.Repository;

import com.springboot.study.backend.domain.board.entity.Board;

import java.util.List;

@Repository
public interface BoardRepository
{
    List<Board> findAll();
    Board findById(Long post_id);
    Board createBoard(Board board);
    Board editBoard(Board board);
    void deleteById(Long post_id);
}