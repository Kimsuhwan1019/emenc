package com.emenc.interior.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// 회원 리포지터리
public interface UserRepository extends JpaRepository<SiteUser, Long> {

    // username으로 회원 조회 (로그인 시 사용)
    Optional<SiteUser> findByUsername(String username);
}
