package com.emenc.interior.user;

import lombok.Getter;

// 회원 권한: ADMIN(관리자), USER(일반회원)
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    // 스프링 시큐리티가 사용하는 권한 문자열
    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
