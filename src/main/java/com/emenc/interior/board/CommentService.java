package com.emenc.interior.board;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emenc.interior.user.SiteUser;

import lombok.RequiredArgsConstructor;

// 댓글 비즈니스 로직
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    public Comment create(FreeBoard board, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBoard(board);
        comment.setAuthor(author);
        comment.setCreateDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 단건 조회 (삭제 시 본인 확인용, 없으면 예외)
    public Comment getComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new DataNotFoundException("댓글이 존재하지 않습니다.");
        }
        return comment.get();
    }

    // 댓글 삭제
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
