package com.pongift.common.data.model.domain.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pongift.common.code.ApiMessageCode;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResOutput {
	Response response = new Response(true, ApiMessageCode.API_MSG_0001.getValue());
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Object page;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Object data;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Object list;

}
