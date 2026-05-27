package com.emenc.interior.user;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 회원 관련 비즈니스 로직
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfig에 등록된 BCrypt 빈

    // 회원 생성: 비밀번호는 BCrypt로 해시 저장, 기본 권한은 USER
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        user.setCreateDate(LocalDateTime.now());
        return userRepository.save(user);
    }
}
