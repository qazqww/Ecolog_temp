package com.thedebuggers.backend.controller.user;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.user.LoginReqDto;
import com.thedebuggers.backend.dto.user.LoginResDto;
import com.thedebuggers.backend.dto.user.TokenDto;
import com.thedebuggers.backend.dto.user.TokenReqDto;
import com.thedebuggers.backend.service.user.AuthService;
import com.thedebuggers.backend.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(value = "인증 관련 API", tags = {"Auth"})
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginDto) {
        boolean firstLogin = false;

        User user = userService.getUserByEmail(loginDto.getEmail());

        if (user == null){
            firstLogin = true;
            user = userService.createUser(loginDto);
        }

        TokenDto tokenDto = authService.login(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCESS_TOKEN, tokenDto.getAccessToken());
        httpHeaders.add(REFRESH_TOKEN, tokenDto.getRefreshToken());

        return new ResponseEntity<>(LoginResDto.of(firstLogin), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "access token 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenReqDto){

        TokenDto tokenDto = authService.reissue(tokenReqDto.getRefreshToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCESS_TOKEN, tokenDto.getAccessToken());
        httpHeaders.add(REFRESH_TOKEN, tokenDto.getRefreshToken());

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "logout")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> logout(Authentication authentication) {

        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        authService.logout(user.getEmail());

        return ResponseEntity.noContent().build();
    }
}
