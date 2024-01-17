package com.pongift.common.data.model.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Receiver {

	String name;            // 수신자 이름
	String phoneNo;         // 수신자 휴대폰번호 - 숫자 형식
	String emailAddress;    // 수신자 이메일 주소

}
