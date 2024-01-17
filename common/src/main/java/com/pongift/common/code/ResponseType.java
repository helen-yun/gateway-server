package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum ResponseType
{

	SUCCESS("SUCCESS");  // 성공

	private String code;

	ResponseType(String code)
	{
		this.code = code;
	}

	public String getCode() { return code; }
}
