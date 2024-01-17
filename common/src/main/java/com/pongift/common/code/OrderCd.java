package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum OrderCd {

	OLD("01"),        // 등록일순
	NEW("02"),        // 등록최신일순
	GOODS_NM("03"),   // 상품명순 > ProductService  내에 구현
	LOW_PRICE("04"),  // 낮은가격순
	HIGH_PRICE("05"); // 낮은가격순

	private String code;

	OrderCd(String code)
	{
		this.code = code;
	}

	public String getCode() { return code; }
}
