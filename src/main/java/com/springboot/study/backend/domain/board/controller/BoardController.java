package com.springboot.study.backend.domain.board.controller;

import com.springboot.study.backend.domain.board.service.BoardService;
import com.springboot.study.backend.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API를 위한 컨트롤러임을 명시
@RequestMapping("/api/board") // 기본 URL 경로 설정
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성 (의존성 주입)
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시물 전체 조회
     * GET /api/board
     * @return 모든 게시물 목록과 HTTP 200 OK 상태
     */
    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.findAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    /**
     * 게시물 단건 조회
     * GET /api/board/{post_id}
     * @param postId 조회할 게시물의 ID
     * @return 해당 게시물과 HTTP 200 OK 상태, 또는 게시물이 없을 경우 HTTP 404 NOT FOUND
     */
    @GetMapping("/{post_id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("post_id") Long post_id) {
        Board board = boardService.findBoardById(post_id);
        // 게시물이 존재하지 않을 경우를 대비하여 처리
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    /**
     * 게시물 작성
     * POST /api/board
     * @param board 작성할 게시물 정보 (JSON 형태의 요청 본문)
     * @return 생성된 게시물 정보와 HTTP 201 CREATED 상태
     */
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        Board createdBoard = boardService.createBoard(board);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    /**
     * 게시물 수정
     * PUT /api/board/{post_id}
     * @param postId 수정할 게시물의 ID
     * @param updatedBoard 수정된 게시물 정보
     * @return 수정된 게시물 정보와 HTTP 200 OK 상태
     */
    @PutMapping("/{post_id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("post_id") Long post_id, @RequestBody Board updatedBoard) {
        Board board = boardService.updateBoard(post_id, updatedBoard);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    /**
     * 게시물 삭제
     * DELETE /api/board/{post_id}
     * @param postId 삭제할 게시물의 ID
     * @return HTTP 204 NO CONTENT 상태
     */
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("post_id") Long post_id) {
        boardService.deleteBoard(post_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}