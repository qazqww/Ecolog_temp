package com.thedebuggers.backend.common.handler;

import com.thedebuggers.backend.common.util.ErrorCode;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("ErrorResponseBody")
public class ErrorResponseBody {

    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ErrorResponseBody of(ErrorCode errorCode) {
        return ErrorResponseBody.builder()
                .status(errorCode.getStatus().value())
                .error(errorCode.getStatus().name())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
