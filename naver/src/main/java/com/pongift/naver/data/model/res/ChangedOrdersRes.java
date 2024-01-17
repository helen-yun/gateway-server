package com.pongift.naver.data.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 변경 상품 주문내역 조회
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangedOrdersRes {
    private String traceId;
    private OrderData data;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;

    @Data
    public static class OrderData{
        private int count;
        private Object more;
        private List<LastChangeStatuses> lastChangeStatuses = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class LastChangeStatuses{
        /**
         * 주문 ID
         */
        private String orderId;
        /**
         * 상품 주문 ID
         */
        private String productOrderId;
        /**
         * 상품 주문 상태
         */
        private String productOrderStatus;
        /**
         * 클레임 구분
         */
        private String claimType;
        /**
         * 클레임 상태
         */
        private String claimStatus;
        /**
         * 최종 변경 일시
         */
        private String lastChangedDate;
        /**
         * 배송지 변경 여부
         */
        private boolean receiverAddressChanged;
        /**
         * 최종 변경 구분
         */
        private String lastChangedType;
        /**
         * 선물 수락 상태 구분
         */
        private String giftReceivingStatus;
    }
}
