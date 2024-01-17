package com.pongift.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제수단
 */
@Getter
@AllArgsConstructor
public enum PaymentGb {

    /**
     * 신용카드
     */
    CREDIT_CARD("01", "신용카드"),
    /**
     * 휴대폰
     */
    PHONE("02", "휴대폰"),
    /**
     * 무통장입금
     */
    CASH("03", "무통장입금"),
    /**
     * 실시간 계좌이체
     */
    REAL_TIME_ACCOUNT_TRANSFER("04", "실시간계좌이체"),
    /**
     * 포인트 결제
     */
    POINT("05", "포인트결제"),
    /**
     * 신용카드 간편결제
     */
    EASY_PAYMENT_CREDIT_CARD("06", "신용카드 간편결제"),
    /**
     * 계좌 간편결제
     */
    EASY_PAYMENT_DEPOSIT("07", "계좌 간편결제"),
    /**
     * 휴대폰 간편결제
     */
    EASY_PAYMENT_PHONE("08", "휴대폰 간편결제");

    private String code;
    private String codeNm;

    public static PaymentGb findByName(String codeNm) {
        if (codeNm == null) {
            return PaymentGb.CREDIT_CARD; // 임시처리
        }
        for (PaymentGb paymentGb : values()) {
            if (codeNm.equals(paymentGb.getCodeNm())) {
                return paymentGb;
            }
        }
        return null;
    }

    public static PaymentGb findByCode(String code) {
        if (code == null) {
            return null;
        }
        for (PaymentGb paymentGb : values()) {
            if (code.equals(paymentGb.getCode())) {
                return paymentGb;
            }
        }
        return null;
    }
}
