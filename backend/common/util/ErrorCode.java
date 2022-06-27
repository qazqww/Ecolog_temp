package com.thedebuggers.backend.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 페이지입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,  "접근할 수 없는 페이지입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않는 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    INVALID_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "토큰 정보가 유효하지 않습니다."),
    LOGIN_DATA_ERROR(HttpStatus.UNAUTHORIZED, "로그인 데이터가 맞지 않습니다."),

    CONTENT_EMPTY(HttpStatus.NO_CONTENT, "해당 콘텐츠가 비어있습니다."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 콘텐츠를 찾을 수 없습니다."),
    CONTENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 콘텐츠에 접근할 수 있는 권한이 없습니다."),
    CONTENT_NOT_FILLED(HttpStatus.BAD_REQUEST, "내용이 채워지지 않았습니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 오류가 발생했습니다." ),

    DUPLICATED_ASSET(HttpStatus.BAD_REQUEST, "이미 구매한 에셋입니다."),
    NOT_ENOUGH_COIN(HttpStatus.BAD_REQUEST, "코인이 부족합니다.");

    private final HttpStatus status;
    private final String message;
}
