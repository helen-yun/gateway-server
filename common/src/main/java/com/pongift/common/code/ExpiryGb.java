package com.pongift.common.code;

import lombok.Getter;

/**
 * <p>
 * 유효기간 구분 코드
 * </p>
 */
@Getter
public enum ExpiryGb {
    THREE_MONTHS("01", 93),
    ONE_YEAR("02", 365),
    THREE_YEAR("04", 1095),
    FIVE_YEARS("03", 1825);

    private final String code;
    private final int value;

    ExpiryGb(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public static int findValueByCode(String code) {
        if (code == null) return 0;
        for (ExpiryGb expiryDay : values()) {
            if(expiryDay.getCode().equals(code)) {
                return expiryDay.getValue();
            }
        }
        return 0;
    }
}
