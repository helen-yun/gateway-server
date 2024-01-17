package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum TradeTp {
    /**
     * 판매
     */
    SALES("01"),

    /**
     * 판매취소
     */
    SALES_CANCEL("02"),

    /**
     * 사용
     */
    USE("03"),

    /**
     * 사용취소
     */
    USE_CANCEL("04"),

    /**
     * 환불
     */
    REFUND("05"),

    /**
     * 선물하기
     */
    GIFT("06"),

    /**
     * 반품요청
     */
    RETURN_REQUESTED("07"),

    /**
     * 반품승인
     */
    RETURN("08");

    private String code;

    TradeTp(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
