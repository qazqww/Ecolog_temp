package com.thedebuggers.backend.auth;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
@RequiredArgsConstructor
public class ELUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);
        if (user != null) {
            ELUserDetails userDetails = new ELUserDetails(user);
            return userDetails;
        }
        return null;
    }
}
