package com.pongift.common.data.model.domain.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pongift.common.data.model.domain.dto.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
	Response response;
	Page page;
}
