package com.emenc.interior.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

// 회원 엔티티 (테이블명: site_user)
@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디 (중복 불가)
    @Column(unique = true)
    private String username;

    // BCrypt로 해시된 비밀번호
    private String password;

    // 이메일 (중복 불가)
    @Column(unique = true)
    private String email;

    // 권한 (문자열로 저장: ADMIN / USER)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // 가입 일시
    private LocalDateTime createDate;
}
