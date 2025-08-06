package com.springboot.study.backend.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity // JPA 엔티티임을 명시
@Getter // Lombok으로 Getter 자동 생성
@Setter // Lombok으로 Setter 자동 생성
@NoArgsConstructor // 인자 없는 기본 생성자 자동 생성
@Table(name = "board") // 매핑될 테이블 이름
public class Board {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가 전략
    private Long post_id; // 게시글 ID

    private Long user_id; // 작성자 ID
    
    private String post_title; // 게시글 제목
    
    private String post_content; // 게시글 내용
    
    private LocalDateTime created_at; // 업로드 날짜
    
    // 게시물 업데이트를 위한 비즈니스 로직
    public void update(String post_title, String post_content) {
        this.post_title = post_title;
        this.post_content = post_content;
        this.created_at = LocalDateTime.now(); // 업로드 날짜도 업데이트
    }
}