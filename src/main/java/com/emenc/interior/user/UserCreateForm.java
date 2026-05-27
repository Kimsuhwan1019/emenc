package com.emenc.interior.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

// 회원가입 폼 (검증용)
@Getter
@Setter
public class UserCreateForm {

    @Size(min = 3, max = 25, message = "사용자 ID는 3~25자여야 합니다.")
    @NotEmpty(message = "사용자 ID는 필수입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
