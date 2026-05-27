package com.emenc.interior.board;

import org.springframework.data.jpa.repository.JpaRepository;

// 댓글 리포지터리
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
