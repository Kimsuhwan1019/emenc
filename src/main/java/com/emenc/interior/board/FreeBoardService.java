package com.emenc.interior.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emenc.interior.user.SiteUser;

import lombok.RequiredArgsConstructor;

// 자유게시판 비즈니스 로직
@RequiredArgsConstructor
@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;

    // 목록 (최신순)
    public List<FreeBoard> getList() {
        return freeBoardRepository.findAllByOrderByCreateDateDesc();
    }

    // 단건 조회 (없으면 예외)
    public FreeBoard getBoard(Long id) {
        Optional<FreeBoard> board = freeBoardRepository.findById(id);
        if (board.isEmpty()) {
            throw new DataNotFoundException("게시글이 존재하지 않습니다.");
        }
        return board.get();
    }

    // 작성 (작성자 지정)
    public FreeBoard create(String subject, String content, SiteUser author) {
        FreeBoard board = new FreeBoard();
        board.setSubject(subject);
        board.setContent(content);
        board.setAuthor(author);
        board.setCreateDate(LocalDateTime.now());
        return freeBoardRepository.save(board);
    }

    // 수정
    public FreeBoard modify(FreeBoard board, String subject, String content) {
        board.setSubject(subject);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        return freeBoardRepository.save(board);
    }

    // 삭제 (연관 댓글도 cascade로 함께 삭제)
    public void delete(FreeBoard board) {
        freeBoardRepository.delete(board);
    }
}
