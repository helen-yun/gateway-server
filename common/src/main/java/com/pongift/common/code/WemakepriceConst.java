package com.pongift.common.code;

/**
 * Created by dplee on 2020/03/20.
 */
public enum WemakepriceConst
{
	INPUT_CHANNEL("GW_API");

	private String value;

	WemakepriceConst(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
