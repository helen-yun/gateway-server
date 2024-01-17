package com.pongift.naver.data.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 발송/반품 처리 request
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispatchProductReq{
    private List<DispatchProductOrders> dispatchProductOrders;

    @Data
    public static class DispatchProductOrders {
        /**
         * 상품 주문 번호
         */
        private String productOrderId;
        /**
         * 배송 방법 코드
         */
        private String deliveryMethod;
        /**
         * 배송일
         */
        private String dispatchDate;
    }
}

