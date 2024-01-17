package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderListParam {
	// platfos 조회 param
	String tradeId;  // 거래
	String productOrderId;  // 상품 거래 ID
	String ledgerId; // 상품권 번호
	
	String tradeTp;  // 거래유형 [01 : 판매 02 : 판매취소 03 : 사용 04 : 사용취소 05 : 환불]
	String orderCd;  // 정렬순서 코드 [01 : 등록순, 02 : 가나다순, 03 : 낮은가격순, 04 : 높은가격순]


	// wemake 조회 param
	String fromDate; // 조회 시작일 required Example:"2020-01-01 00:00:00" string
	String toDate; // 조회 종료일 required Example:"2020-01-01 23:59:59" string
	String type = "NEW"; // required 조회 유형 (NEW:신규주문 ,CONFIRM:발송처리대상, DELIVERY:배송중, COMPLETE:배송완료) Example : "string" string
	String searchDateType; // optional 조회기간 유형리 (NEW:결제일(기본), CONFIRM:주문확인일, COMPLETE:배송완료일 Example : "string" string

	boolean approveYn = false;  // 발송/승인처리 여부
}
