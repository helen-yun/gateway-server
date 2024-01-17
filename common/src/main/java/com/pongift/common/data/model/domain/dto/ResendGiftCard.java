package com.pongift.common.data.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResendGiftCard {
    /**
     * 휴대폰 번호
     */
    private String phoneNo;
    /**
     * 알림톡 전송 여부
     */
    private String sendTalkSt;
    /**
     * 상품권 거래 유형
     */
    private String tradeTp;
    /**
     * 거래주문번호
     */
    private String orderNo;
    /**
     * 거래주문번호 상세
     */
    private String detailNo;    
    /**
     * 채널 구분
     */
    private String channelGb;
}
