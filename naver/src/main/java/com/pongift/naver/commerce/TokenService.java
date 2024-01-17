package com.pongift.naver.commerce;

import com.pongift.common.utils.DateUtils;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.CommerceToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {
    private final CommerceWebClient commerceWebClient;

    @Value("${naver.commerce.clientId}")
    private String clientId;
    @Value("${naver.commerce.clientSecret}")
    private String clientSecret;

    @Getter
    private String accessToken; // 커머스 API 인증 토큰
    @Getter
    private long expiresIn; // 인증 유효 기간(초)
    @Getter
    private LocalDateTime expiresDate; // 인증 유효 시간

    /**
     * 커머스 API 토큰 초기화
     */
    public void initCommerceToken() {
        //네이버 timestamp 이슈로 인해 -2초 설정 후 요청
        long timestamp = System.currentTimeMillis() - 2000;
        String password = StringUtils.joinWith("_", clientId, timestamp);
        String hashedPw = BCrypt.hashpw(password, clientSecret);
        CommerceToken commerceToken = commerceWebClient.token(clientId, Base64.getUrlEncoder().encodeToString(hashedPw.getBytes(StandardCharsets.UTF_8)), timestamp);
        log.info("commerceToken : {}", commerceToken);

        accessToken = commerceToken.getAccessToken();
        expiresIn = commerceToken.getExpiresIn();
        expiresDate = DateUtils.getDateTimeAfterSeconds(commerceToken.getExpiresIn());
    }

    /**
     * 인증 유효 시간 검증 (30분 미만 시 토큰 갱신 필요)
     *
     * @return boolean
     */
    public boolean validateExpiresDate() {
        if (expiresDate == null) return false;
        LocalDateTime currentDateTime = LocalDateTime.now();
        long differenceInMinutes = ChronoUnit.MINUTES.between(currentDateTime, expiresDate);
        log.debug("differenceInMinutes => {}", differenceInMinutes);

        return differenceInMinutes >= 30;
    }
}
