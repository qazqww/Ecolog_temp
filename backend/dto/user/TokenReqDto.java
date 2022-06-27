package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@ApiModel("RefreshTokenRequest")
@RequiredArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TokenReqDto {
    String refreshToken;
}
