package com.pongift.common.code;

/**
 * Created by dplee on 2020/03/20.
 */
public enum AuctionConst
{
	SECURITY_URL("http://www.auction.co.kr/Security"),
	SECURITY_LOCAL_PART("EncryptedTicket"),
	INPUT_CHANNEL("GW_API");

	private String value;

	AuctionConst(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
