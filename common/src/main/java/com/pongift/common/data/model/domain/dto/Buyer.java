package com.pongift.common.data.model.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Buyer{
	String buyerId;            // 구매자 아이디
	String name;            // 구매자 이름
	String phoneNo;         // 구매자 휴대폰번호 숫자 형식
	String emailAddress;    // 구매자 이메일 주소
	String paymentGb;       // 구매자 결제 수단 구분

}
