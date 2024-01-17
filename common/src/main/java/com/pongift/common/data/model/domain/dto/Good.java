package com.pongift.common.data.model.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Good implements Comparable<Good> {
	String productOrderId;      // 상품 거래 ID
	String goodsId;             // 상품ID
	List<Category> categories;  // 카테고리
	String goodsNm;             // 상품명
	int stockCnt;               // 재고수량
	int salePrice;              // 실판매가
	int retailPrice;            // 소비자가격(정상가)
	String exhibitSt;           // 상품 전시 상태
		// 01 : 전시대기
		// 02 : 전시판매
		// 03 : 전시중지
	String html;                // 상품 정보
	String goodsImage;          // 대표이미지
	String regDate;             // 판매 일시(YYYYMMDD24HHMISS)
	String modDate;             // 변경일시

	Long stockNo;               // stock update 위한 no
	int productGroupNoticeNo;


	int saleCnt;    //	거래수량
	// 상품 업데이트 위한 필수값
	String saleStartDate;       // 판매기간 시작일 (yyyy-MM-dd HH:00) ※ 판매시작 이후 변경 불가
	String saleEndDate;         //판매기간 종료일 (yyyy-MM-dd HH:00)
	int sellerProdCode = 100102; // 위메프 업체상품코드 (임의코드) > 상품목록 호출시 이용됨

	/**
	 * 제휴몰 관련 parameter 통일을 위해 추가
	 */
	String infoUrl;
	String storeUrl;
	String storeName;
	String storeId;

	@Override public int compareTo(Good good) { return this.goodsNm.compareTo(good.goodsNm); }
}
