package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClaimListParam {
	// platfos 조회 param
	String tradeId;  // 거래
	String tradeTp;  // 거래유형 [01 : 판매 02 : 판매취소 03 : 사용 04 : 사용취소 05 : 환불]
	String page;
	String pageSize;
	String orderCd;  // 정렬순서 코드 [01 : 등록순, 02 : 가나다순, 03 : 낮은가격순, 04 : 높은가격순]

	// wemake 조회 param
	String fromDate; // 조회 시작일 required Example:"2020-01-01 00:00:00" string
	String toDate; // 조회 종료일 required Example:"2020-01-01 23:59:59" string
	String claimType = "CANCEL"; // 클레임 종류 (CANCEL:취소, EXCHANGE:교환, RETURN:반품)
	String claimStatus = "APPROVE"; // 클레임 상태 (REQUEST:신청, APPROVE:승인, PENDING:보류, REJECT:거부, WITHDRAW:철회)
	String searchDateType; // 조회기간 유형 (REQUEST:신청일(기본), APPROVE:승인일, PENDING:보류설정일, REJECT:거부일, WITHDRAW:철회일

	boolean approveYn = false;  // 발송/승인처리 여부
}
