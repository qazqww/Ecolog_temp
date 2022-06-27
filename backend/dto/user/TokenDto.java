package com.thedebuggers.backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
