package com.emenc.interior.board;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

// 댓글 작성 폼 (검증용)
@Getter
@Setter
public class CommentForm {

    @NotEmpty(message = "댓글 내용은 필수입니다.")
    private String content;
}
