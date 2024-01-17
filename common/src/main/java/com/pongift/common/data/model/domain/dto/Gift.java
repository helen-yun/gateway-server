package com.pongift.common.data.model.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@JsonInclude(Include.NON_DEFAULT)
public class Gift {
	boolean success = false;
	String productOrderId;  // 상품 거래 ID
	String barcodeNo;		// 바코드 번호
	String expiryDate;		// 유효기간 [형식 : yyyyMMdd]

}
