package com.pongift.common.code;

/**
 * The enum Bot commongwa err code.
 */
public enum ChannelGb
{

	NAVER("01"),    // 01	naver,
	AUCTION("02"),    // 02	auction,
	EBAY("03"),    // 03	ebay,
	WEMAKEPRICE("04"),    // 04 wemakeprice,
	SSG("05");    // 05	ssg

	private String code;

	ChannelGb(String code)
	{
		this.code = code;
	}

	public String getCode() { return code; }
}
