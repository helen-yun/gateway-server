package com.pongift.naver.data.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


/**
 * (v2) 상품 등록 Responses
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegProductsRes {
    /**
     * 원상품번호
     */
    private long originProductNo;
    /**
     * 스마트스토어 채널 상품번호
     */
    private long smartstoreChannelProductNo;
    /**
     * 윈도 채널 상품번호
     */
    private long windowChannelProductNo;
}
