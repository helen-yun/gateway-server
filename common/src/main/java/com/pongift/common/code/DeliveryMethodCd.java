package com.pongift.common.code;

public enum DeliveryMethodCd {
    DELIVERY("DELIVERY"),    //택배, 등기, 소포
    GDFW_ISSUE_SVC("GDFW_ISSUE_SVC"),    //굿스플로 송장 출력
    VISIT_RECEIPT("VISIT_RECEIPT"),    //방문 수령
    DIRECT_DELIVERY("DIRECT_DELIVERY"),    //직접 전달
    QUICK_SVC("QUICK_SVC"),    //퀵서비스
    NOTHING("NOTHING"),       //배송 없음
    RETURN_DESIGNATED("RETURN_DESIGNATED"), //지정 반품 택배
    RETURN_DELIVERY("RETURN_DELIVERY"), //일반 반품 택배
    RETURN_INDIVIDUAL("RETURN_INDIVIDUAL"), //직접 반송
    RETURN_MERCHANT("RETURN_MERCHANT"), //판매자 직접 수거
    UNKNOWN("UNKNOWN"); //일수없음(예외 처리에 사용)


    private String code;

    DeliveryMethodCd(String code)
    {
        this.code = code;
    }

    public String getValue() { return code; }
}
