package com.pongift.naver.code;

public enum ChangedType {
    PAY_WAITING("PAY_WAITING"),     //결제 대기
    PAYED("PAYED"),	//결제 완료
    CANCELED("CANCELED"), //취소 요청
    EXCHANGE_OPTION("EXCHANGE_OPTION"),	//옵션 변경
    DELIVERY_ADDRESS_CHANGED("DELIVERY_ADDRESS_CHANGED"),//	배송지 변경
    GIFT_RECEIVED("GIFT_RECEIVED"),	//선물 수락
    RECEIVED("RECEIVED"), //선물 수락 완료
    CLAIM_REJECTED("CLAIM_REJECTED"),	//클레임 철회
    DISPATCHED("DISPATCHED"),	//발송 처리
    CLAIM_REQUESTED("CLAIM_REQUESTED"),	//클레임 요청
    COLLECT_DONE("COLLECT_DONE"),	//수거 완료
    CLAIM_HOLDBACK_RELEASED("CLAIM_HOLDBACK_RELEASED"),	//클레임 보류 해제
    CLAIM_COMPLETED("CLAIM_COMPLETED"),	//클레임 완료
    PURCHASE_DECIDED("PURCHASE_DECIDED"),	//구매 확정
    HOPE_DELIVERY_INFO_CHANGED("HOPE_DELIVERY_INFO_CHANGED"),	//배송 희망일 변경
    CLAIM_REDELIVERING("CLAIM_REDELIVERING"),	//교환 재배송 처리
    RETURN_REQUEST("RETURN_REQUEST"),   //반품 요청
    RETURN_DONE("RETURN_DONE"), //반품 완료
    CANCEL_DONE("CANCEL_DONE");     //취소 처리 완료

    final String code;

    ChangedType(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }
}
