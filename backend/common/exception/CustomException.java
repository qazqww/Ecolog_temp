package com.thedebuggers.backend.common.exception;

import com.thedebuggers.backend.common.util.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
