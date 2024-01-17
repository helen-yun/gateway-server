package com.pongift.naver.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommerceCommonRes {
    private String code;
    private String message;
    private String traceId;
    private Object data;
}
