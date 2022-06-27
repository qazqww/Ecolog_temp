package com.thedebuggers.backend.service.user;

import com.thedebuggers.backend.dto.user.LoginReqDto;
import com.thedebuggers.backend.dto.user.TokenDto;

public interface AuthService {
    TokenDto login(LoginReqDto loginDto);

    void logout(String userEmail);

    TokenDto reissue(String refreshToken);
}
