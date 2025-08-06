package com.springboot.study.backend.domain.board.service;

import com.springboot.study.backend.domain.board.entity.Board;
import com.springboot.study.backend.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service // Spring Bean으로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시물 전체 조회
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }
    
    // 게시물 단건 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long postId) {
        return boardRepository.findById(postId);
    }
    
    // 게시물 작성
    @Transactional
    public Board createBoard(Board board) {
        Board createdBoard = boardRepository.createBoard(board);

        createdBoard.setCreated_at(LocalDateTime.now());
        // createdAt 필드는 DB에 저장되기 직전에 설정
        // 비즈니스 로직: 게시물 제목 길이 검증
        // 예외처리는 컨트롤러에서 하거나 글로벌 예외 핸들러에서 처리할 것. 팀원들과 글로벌 예외를 처리할 지 상의 후 결정.
        if (createdBoard.getPost_title() == null || createdBoard.getPost_title().length() > 255) {
            throw new IllegalArgumentException("게시물 제목이 유효하지 않습니다.");
        }
        return createdBoard;
    }

    // 게시물 수정
    @Transactional
    public Board updateBoard(Long postId, Board updatedBoard) {
        Board board = boardRepository.findById(postId);
        // 게시물 업데이트 비즈니스 로직
        board.update(updatedBoard.getPost_title(), updatedBoard.getPost_content());
        
        // save() 메서드가 내부적으로 업데이트 로직을 처리합니다.
        return boardRepository.editBoard(board);
    }
    
    // 게시물 삭제
    @Transactional
    public void deleteBoard(Long postId) {
        boardRepository.deleteById(postId);
    }
}