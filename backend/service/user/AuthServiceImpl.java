package com.thedebuggers.backend.service.user;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thedebuggers.backend.auth.JwtTokenUtil;
import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.ErrorCode;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.repository.user.UserRepository;
import com.thedebuggers.backend.dto.user.LoginReqDto;
import com.thedebuggers.backend.dto.user.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;

    @Override
    public TokenDto login(LoginReqDto loginDto) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();

        String userEmail = loginDto.getEmail();
        User user = userRepository.findByEmail(userEmail).get();

        if (user.getLoginType() != loginDto.getLoginType()) {
            throw new CustomException(ErrorCode.LOGIN_DATA_ERROR);
        }

        String accessToken = JwtTokenUtil.getAccessToken(userEmail);
        String refreshToken = JwtTokenUtil.getRefreshToken(userEmail);

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        values.set(userEmail, refreshToken, JwtTokenUtil.getRefreshExpirationTime(), TimeUnit.SECONDS);

        return tokenDto;
    }

    @Override
    public void logout(String userEmail) {
        redisTemplate.delete(userEmail);
    }

    @Override
    public TokenDto reissue(String refreshToken){

        ValueOperations<String, String> values = redisTemplate.opsForValue();

        JWTVerifier verifier = JwtTokenUtil.getVerifier();

        JwtTokenUtil.handleError(refreshToken);
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String userEmail = decodedJWT.getSubject();

        if (!userRepository.findByEmail(userEmail).isPresent() || !values.get(userEmail).equals(refreshToken))
            throw new CustomException(ErrorCode.UNAUTHORIZED);

        String accessToken = JwtTokenUtil.getAccessToken(userEmail);

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return tokenDto;
    }
}
