package com.pongift.naver.data.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * E-Coupon 관련
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ECouponReq {
    private List<ECouponDetail> data;

    @Getter
    @Setter
    public static class ECouponDetail{
        /**
         * 상품 주문 번호(required)
         */
        private String productOrderId;
        /**
         * E쿠폰 번호(required)
         */
        private String eCouponNo;
        /**
         * 바코드 번호(최대 2개)
         */
        private List<String> barcodeNos;
        /**
         * 쿠폰 상태(required)
         */
        private String eCouponStatusType;
        /**
         * 사용 일시.(E쿠폰 상대를 USED로 변경할 때만 유효)
         */
        private String usedDate;
        /**
         * 유효기간 시작일
         */
        private String validStartDate;
        /**
         * 유효기간 종료일
         */
        private String validEndDate;
    }
}
