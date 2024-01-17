package com.pongift.common.data.model.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonRequest {
	@JsonIgnore
	int page;
	@JsonIgnore
	int pageSize;

}
