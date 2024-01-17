package com.pongift.naver.data.model.req;

import com.pongift.naver.data.model.commerce.OriginProduct;
import com.pongift.naver.data.model.commerce.SmartstoreChannelProduct;
import lombok.*;

/**
 * 스마트 스토어 상품 등록/수정 요청 객체
 */
@Data
@AllArgsConstructor
public class CommerceProductsReq {
    private OriginProduct originProduct;
    private SmartstoreChannelProduct smartstoreChannelProduct;
}
