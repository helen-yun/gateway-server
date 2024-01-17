package com.pongift.common.data.model.domain.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pongift.common.code.ApiMessageCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	// todo remove 임시로 세팅함 > 오류방지
	String serviceId;
	String code;

	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	boolean success = true;
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String message = ApiMessageCode.API_MSG_0001.getValue();

	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public void Success() {
		this.success = true;
	}
	public void Error() {
		this.success = false;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
