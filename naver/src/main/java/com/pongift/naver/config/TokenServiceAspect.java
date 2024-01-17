package com.pongift.naver.config;

import com.pongift.naver.commerce.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class TokenServiceAspect {
    private final TokenService tokenService;

    @Before("execution(* com.pongift.naver.controller..*(..))")
    public void updateAccessToken(JoinPoint joinPoint) {
        log.debug("BEFORE == " + joinPoint.getSignature());
        if (!tokenService.validateExpiresDate() || !StringUtils.hasText(tokenService.getAccessToken())) {
            log.debug("토큰 새로고침!!");
            tokenService.initCommerceToken();
        }
    }
}
