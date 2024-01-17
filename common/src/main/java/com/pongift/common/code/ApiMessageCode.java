package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum ApiMessageCode {

	API_MSG_0001("처리되었습니다."),
	API_MSG_0002("결과가 없습니다."),
	API_MSG_0003("등록되었습니다."),
	API_MSG_0004("수정되었습니다."),
	API_MSG_0005("삭제되었습니다."),
	API_MSG_0006("저장되었습니다."),

	API_MSG_0007("처리 대상이 없습니다."),
	API_MSG_0008("PONGIFT 주문처리 API 호출중 오류가 발생하였습니다"),
	API_MSG_0009("PONGIFT 취소처리 API 호출중 오류가 발생하였습니다"),

	API_MSG_1000("상품아이디가 없습니다"),
	API_MSG_1001("유효한 물품이 아니거나 등록된 물품이 아닙니다"),

	API_ERROR_MSG_0001("오류가 발생하였습니다."),
	API_ERROR_MSG_0002("ApiClient 실행중 알수 없는 오류가 발생하였습니다.");

	private String value;

	ApiMessageCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
