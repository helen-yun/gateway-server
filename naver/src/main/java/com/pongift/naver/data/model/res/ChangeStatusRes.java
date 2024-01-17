package com.pongift.naver.data.model.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeStatusRes {
    /**
     * 코드
     */
    private String code;
    /**
     * 메시지
     */
    private String message;
    /**
     * 데이터 정보
     */
    private Object data;
    /**
     * error
     */
    private Object invalidInputs;
    private String timestamp;
}
