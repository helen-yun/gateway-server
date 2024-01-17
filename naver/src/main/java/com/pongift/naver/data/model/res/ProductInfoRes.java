package com.pongift.naver.data.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pongift.naver.data.model.commerce.OriginProduct;
import com.pongift.naver.data.model.commerce.SmartstoreChannelProduct;
import lombok.*;

/**
 * 상품 조회
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoRes {
    private OriginProduct originProduct;
    private SmartstoreChannelProduct smartstoreChannelProduct;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;
}




