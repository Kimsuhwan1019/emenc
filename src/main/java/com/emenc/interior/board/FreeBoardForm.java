package com.emenc.interior.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

// 글 작성/수정 폼 (검증용)
@Getter
@Setter
public class FreeBoardForm {

    @NotEmpty(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이내여야 합니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}
