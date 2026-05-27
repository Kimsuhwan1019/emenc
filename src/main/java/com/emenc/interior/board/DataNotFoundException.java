package com.emenc.interior.board;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 데이터(글/댓글)가 없을 때 던지는 예외 → 404 응답
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "데이터를 찾을 수 없습니다.")
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
