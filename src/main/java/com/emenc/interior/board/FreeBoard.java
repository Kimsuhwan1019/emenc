package com.emenc.interior.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.emenc.interior.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

// 자유게시판 글 엔티티 (테이블명: free_board)
@Getter
@Setter
@Entity
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(length = 200)
    private String subject;

    // 내용 (길게 저장)
    @Column(columnDefinition = "TEXT")
    private String content;

    // 작성자 (회원)
    @ManyToOne
    private SiteUser author;

    // 작성 일시
    private LocalDateTime createDate;

    // 수정 일시
    private LocalDateTime modifyDate;

    // 댓글 목록 (글 삭제 시 댓글도 함께 삭제)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();
}
