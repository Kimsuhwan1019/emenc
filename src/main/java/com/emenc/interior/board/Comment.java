package com.emenc.interior.board;

import java.time.LocalDateTime;

import com.emenc.interior.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

// 댓글 엔티티 (테이블명: comment)
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 작성자 (회원)
    @ManyToOne
    private SiteUser author;

    // 소속 게시글
    @ManyToOne
    private FreeBoard board;

    // 작성 일시
    private LocalDateTime createDate;
}
