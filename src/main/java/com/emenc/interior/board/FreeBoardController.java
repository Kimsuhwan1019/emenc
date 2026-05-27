package com.emenc.interior.board;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.emenc.interior.user.SiteUser;
import com.emenc.interior.user.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// 자유게시판 컨트롤러
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;
    private final CommentService commentService;
    private final UserRepository userRepository; // 현재 로그인 사용자(작성자) 조회용

    // 글 목록 (최신순)
    @GetMapping({"", "/", "/list"})
    public String list(Model model) {
        List<FreeBoard> boardList = freeBoardService.getList();
        model.addAttribute("boardList", boardList);
        return "board_list";
    }

    // 글 상세 (글 + 댓글 목록 + 댓글 입력폼)
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, CommentForm commentForm, Model model) {
        FreeBoard board = freeBoardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    // 글쓰기 폼 (인증 필요는 SecurityConfig에서 처리)
    @GetMapping("/create")
    public String createForm(FreeBoardForm freeBoardForm, Model model) {
        model.addAttribute("actionUrl", "/board/create"); // 폼 전송 대상
        return "board_form";
    }

    // 글쓰기 처리
    @PostMapping("/create")
    public String create(@Valid FreeBoardForm freeBoardForm, BindingResult bindingResult,
            Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionUrl", "/board/create");
            return "board_form";
        }
        SiteUser author = getCurrentUser(principal);
        freeBoardService.create(freeBoardForm.getSubject(), freeBoardForm.getContent(), author);
        return "redirect:/board/list";
    }

    // 글 수정 폼 (작성자 본인만)
    @GetMapping("/modify/{id}")
    public String modifyForm(FreeBoardForm freeBoardForm, @PathVariable("id") Long id,
            Principal principal, Model model) {
        FreeBoard board = freeBoardService.getBoard(id);
        checkAuthor(board.getAuthor(), principal); // 본인 확인
        // 기존 값 채우기
        freeBoardForm.setSubject(board.getSubject());
        freeBoardForm.setContent(board.getContent());
        model.addAttribute("actionUrl", "/board/modify/" + id);
        return "board_form";
    }

    // 글 수정 처리 (작성자 본인만)
    @PostMapping("/modify/{id}")
    public String modify(@Valid FreeBoardForm freeBoardForm, BindingResult bindingResult,
            @PathVariable("id") Long id, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionUrl", "/board/modify/" + id);
            return "board_form";
        }
        FreeBoard board = freeBoardService.getBoard(id);
        checkAuthor(board.getAuthor(), principal);
        freeBoardService.modify(board, freeBoardForm.getSubject(), freeBoardForm.getContent());
        return String.format("redirect:/board/%s", id);
    }

    // 글 삭제 (작성자 본인만)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        FreeBoard board = freeBoardService.getBoard(id);
        checkAuthor(board.getAuthor(), principal);
        freeBoardService.delete(board);
        return "redirect:/board/list";
    }

    // 댓글 작성 (로그인 필요)
    @PostMapping("/comment/create/{boardId}")
    public String createComment(@Valid CommentForm commentForm, BindingResult bindingResult,
            @PathVariable("boardId") Long boardId, Principal principal, Model model) {
        FreeBoard board = freeBoardService.getBoard(boardId);
        if (bindingResult.hasErrors()) {
            // 검증 실패 시 상세 화면 다시 표시
            model.addAttribute("board", board);
            return "board_detail";
        }
        SiteUser author = getCurrentUser(principal);
        commentService.create(board, commentForm.getContent(), author);
        return String.format("redirect:/board/%s", boardId);
    }

    // 댓글 삭제 (댓글 작성자 본인만)
    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id, Principal principal) {
        Comment comment = commentService.getComment(id);
        checkAuthor(comment.getAuthor(), principal);
        Long boardId = comment.getBoard().getId();
        commentService.delete(comment);
        return String.format("redirect:/board/%s", boardId);
    }

    // 현재 로그인 사용자 조회
    private SiteUser getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new DataNotFoundException("로그인 사용자를 찾을 수 없습니다."));
    }

    // 작성자 본인인지 확인 (아니면 403)
    private void checkAuthor(SiteUser author, Principal principal) {
        if (author == null || !author.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }
    }
}
