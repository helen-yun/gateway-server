package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsageParam {
	private String tradeId;
	private String ledgerId;
	private String productOrderId; // 상품주문번호
	private String giftNo; // 상품권 번호
	private String ecouponNo; // E-쿠폰번호



}
