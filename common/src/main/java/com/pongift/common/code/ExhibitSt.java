package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum ExhibitSt {
    SUSP("01"),  // 01 : 전시대기
    SALE("02"),  // 02 : 전시판매
    STOP("03"),  // 03 : 전시중지
    OSTK("04"),  // 04 : 전시품절
    ;
    
    private final String code;
    
    ExhibitSt(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
