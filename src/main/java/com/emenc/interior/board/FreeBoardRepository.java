package com.emenc.interior.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// 자유게시판 글 리포지터리
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    // 최신순(작성일 내림차순) 전체 목록
    List<FreeBoard> findAllByOrderByCreateDateDesc();
}
