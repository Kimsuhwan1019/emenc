package com.emenc.interior.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 스프링 시큐리티 설정
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 인가 규칙
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스 허용
                .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                // 공개 페이지 허용 (/error: 비로그인도 404/500 등 오류 페이지 열람 가능)
                .requestMatchers("/", "/user/signup", "/user/signin",
                        "/greeting", "/store", "/location", "/error").permitAll()
                // H2 콘솔 허용
                .requestMatchers("/h2-console/**").permitAll()
                // 게시판: 글쓰기/수정/삭제/댓글은 인증 필요 (목록·상세보다 먼저 평가)
                .requestMatchers("/board/create", "/board/modify/**",
                        "/board/delete/**", "/board/comment/**").authenticated()
                // 게시판: 목록·상세 조회는 비로그인도 허용
                .requestMatchers("/board", "/board/list", "/board/{id}").permitAll()
                // 그 외는 인증 필요
                .anyRequest().authenticated())
            // H2 콘솔은 CSRF 예외 처리
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            // H2 콘솔 프레임 표시 허용 (같은 출처)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            // 커스텀 로그인 페이지, 성공 시 홈으로
            .formLogin(form -> form
                .loginPage("/user/signin")
                .defaultSuccessUrl("/"))
            // 로그아웃: /user/logout, 성공 시 홈으로 + 세션 무효화
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true));

        return http.build();
    }

    // 비밀번호 BCrypt 인코더
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 매니저 (폼 로그인 처리에 사용)
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
