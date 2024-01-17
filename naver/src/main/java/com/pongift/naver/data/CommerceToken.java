package com.pongift.naver.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 네이버 커머스 토큰
 */
@Data
public class CommerceToken {
    @JsonProperty("access_token")
    private String accessToken; // 인증 토큰
    @JsonProperty("expires_in")
    private long expiresIn; // 인증 유효 기간(초)
    @JsonProperty("token_type")
    private String tokenType; // 인증 토큰 종류
}
