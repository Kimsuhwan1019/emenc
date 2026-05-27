package com.emenc.interior;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 사이트 기본 페이지 컨트롤러
@Controller
public class MainController {

    // 홈
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 인사말
    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    // 매장소개
    @GetMapping("/store")
    public String store() {
        return "store";
    }

    // 오시는길
    @GetMapping("/location")
    public String location() {
        return "location";
    }

    // 견적문의 (다음 단계 구현 예정 - 준비 중 안내)
    @GetMapping("/inquiry")
    public String inquiry(Model model) {
        model.addAttribute("pageTitle", "견적문의");
        return "prepare";
    }

    // 자유게시판 (다음 단계 구현 예정 - 준비 중 안내)
    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("pageTitle", "자유게시판");
        return "prepare";
    }
}
