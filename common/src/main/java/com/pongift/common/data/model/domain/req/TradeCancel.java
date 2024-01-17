package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pongift.common.code.CancelSaleReasonCd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeCancel {
    /**
     * <p>채널 주문 번호</p>
     * <p>네이버: ProductOrderID</p>
     * <p>옥션: OrderNo</p>
     */
    private String channelOrderNo;
    /**
     * <p>채널 상품 ID<br>(channelId와 동일. 네이밍 혼동을 피하기 위해 이름 변경)</p>
     * <p>옥션 only: ItemID</p>
     */
    private String channelGoodsId;
    /**
     * <p>채널 주문 옵션 번호 (위메프 only)</p>
     */
    private long orderOptionNo;
    /**
     * <p>취소 사유 코드</p>
     */
    private CancelSaleReasonCd cancelSaleReasonCd;
}
