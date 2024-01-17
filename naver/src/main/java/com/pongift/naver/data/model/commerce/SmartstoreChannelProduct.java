package com.pongift.naver.data.model.commerce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SmartstoreChannelProduct {
    /**
     * 채널 상품 전용 상품명
     */
    private String channelProductName;
    /**
     * 공지사항
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long bbsSeq;
    /**
     * 알림받기 동의 회원 전용 상품 여부
     */
    private boolean storeKeepExclusiveProduct;
    /**
     * 네이버쇼핑 등록 여부.
     * 네이버 쇼핑 광고주가 아닌 경우에는 false로 저장됩니다.
     */
    private boolean naverShoppingRegistration;
    /**
     * 전시 상태 코드(스마트스토어 채널 전용)
     */
    private ChannelProductDisplayStatusType channelProductDisplayStatusType;

    /**
     * 전시 상태 코드(스마트스토어 채널 전용)
     * WAIT : 전시대기
     * ON : 전시중
     * SUSPENSION : 전시 중지
     */
    public enum ChannelProductDisplayStatusType {
        WAIT, ON, SUSPENSION

    }
}
