package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum ApprovalSt
{

	NON_APPROVAL("01"),  // 01 : 미승인
	APPROVAL("02"),      // 02 : 승인
	RETURN("03");        // 03 : 반려

	private String code;

	ApprovalSt(String code)
	{
		this.code = code;
	}

	public String getCode() { return code; }
}
