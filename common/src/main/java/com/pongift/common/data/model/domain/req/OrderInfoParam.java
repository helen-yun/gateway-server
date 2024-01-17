package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInfoParam {
	int bundleNo; // 배송번호 required Example : 0 integer (int64)

	String tradeId;         // 주문요청번호
	String productOrderId;  // 상품 주문 번호
}
