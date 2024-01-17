package com.pongift.naver.code;

public enum ECouponType {
    NOT_USED("NOT_USED"),
    CANCEL("CANCEL"),
    USED("USED"),
    UNUSABLE("UNUSABLE");

    final String code;

    ECouponType(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }
}
