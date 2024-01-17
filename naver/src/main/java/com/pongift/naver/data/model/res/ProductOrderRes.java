package com.pongift.naver.data.model.res;

import lombok.*;

import java.util.List;

/**
 * 발송/반품 요청
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductOrderRes {
    private String traceId;
    private OrderData data;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;

    @Getter
    @Setter
    public static class OrderData {
        /**
         * 성공 상품 주문 번호
         */
        private List<String> successProductOrderIds;
        /**
         * 실패 상품 주문 번호
         */
        private List<FailProductOrderInfos> failProductOrderInfos;

        @Getter
        @Setter
        public static class FailProductOrderInfos{
            private String productOrderId;
            private String code;
            private String message;
        }
    }
}
