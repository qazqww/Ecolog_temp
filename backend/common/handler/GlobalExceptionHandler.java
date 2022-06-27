package com.thedebuggers.backend.common.handler;

import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Custom Exception
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseBody> customException(final CustomException ex) {
        ex.printStackTrace();
        log.error("customException: {}", ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getStatus().value()).body(ErrorResponseBody.of(ex.getErrorCode()));
    }

    // 401 Unauthorized
    @ExceptionHandler(AuthorizationServiceException.class)
    protected ResponseEntity<ErrorResponseBody> authorizationException(final AuthorizationServiceException ex) {
        ex.printStackTrace();
        log.error("authorizationException: {}", ex.getMessage());
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatus().value()).body(ErrorResponseBody.of(ErrorCode.UNAUTHORIZED));
    }

    // 404 Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponseBody> notFoundException(final NoHandlerFoundException ex) {
        ex.printStackTrace();
        log.error("notFoundException: {}", ex.getMessage());
        return ResponseEntity.status(ErrorCode.NOT_FOUND.getStatus().value()).body(ErrorResponseBody.of(ErrorCode.NOT_FOUND));
    }

    // 500 Server Error
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseBody> serverErrorException(final Exception ex) {
        ex.printStackTrace();
        log.error("serverErrorException: {}", ex.getMessage());
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value()).body(ErrorResponseBody.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
