package com.pongift.common.data.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Trade {
    /**
     * <p>유통 채널 구분</p>
     * <ul>
     *     <li>01:naver</li>
     *     <li>02:auction</li>
     *     <li>03:ebay</li>
     *     <li>04:wemakeprice</li>
     *     <li>05:ssg</li>
     * </ul>
     */
    String channelGb;
    /**
     * <p>거래 ID</p>
     */
    String tradeId;
    /**
     * <p>판매 상태</p>
     * <ul>
     *     <li>01:판매</li>
     *     <li>02:판매 승인</li>
     *     <li>03:판매 승인 취소 요청</li>
     *     <li>04:취소</li>
     * </ul>
     */
    String tradeTp;
    /**
     * <p>거래 일시 형식 : yyyyMMddHHmmss</p>
     */
    String tradeDate;
    
    ArrayList<Good> goodsList;
    Good goods;
    Buyer buyer;
    Receiver receiver;
    
}
