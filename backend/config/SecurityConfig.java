package com.thedebuggers.backend.config;


import com.thedebuggers.backend.auth.ELUserDetailsService;
import com.thedebuggers.backend.auth.JwtAuthenticationFilter;
import com.thedebuggers.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity //Spring Security 활성화-스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ELUserDetailsService elUserDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // DAO 기반으로 Authentication Provider를 생성
    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(this.elUserDetailsService);
        return daoAuthenticationProvider;
    }

    // DAO 기반의 Authentication Provider가 적용되도록 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService))

                .authorizeRequests()
                .antMatchers("/api/v1/user/**", "/api/v1/community/**", "/api/v1/plogging/**", "/api/v1/trash_can/**", "/api/v1/asset/**").authenticated()
                .anyRequest().permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
