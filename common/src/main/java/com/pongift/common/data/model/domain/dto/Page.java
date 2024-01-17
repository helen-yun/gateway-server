package com.pongift.common.data.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Page {
	int totalPage;  // 결과 총 페이지 수
	int page;       // 결과 페이지

	public Page(int page, int totalPage){
		this.page = page;
		this.totalPage = totalPage;
	}
}
