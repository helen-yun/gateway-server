package com.pongift.naver.data.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeStatusReq {
    /**
     * 원상품번호
     */
    private long originProductNo;
    /**
     * 상품 판매 상태 코드
     */
    private StatusType statusType;
    public enum StatusType {
        /**
         * 상태 코드 값
         * SALE : 판매중
         * OUTOFSTOCK : 품절
         * SUSPENSION : 판매 중지
         */
        SALE, OUTOFSTOCK, SUSPENSION

    }
}
