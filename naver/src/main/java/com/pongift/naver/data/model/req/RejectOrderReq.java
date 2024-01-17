package com.pongift.naver.data.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RejectOrderReq {
    /**
     * 상품 주문 번호
     */
    private String productOrderId;
    /**
     * 반품 사유
     */
    private String rejectReturnReason;
}
