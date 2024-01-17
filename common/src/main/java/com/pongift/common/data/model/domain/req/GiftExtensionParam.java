package com.pongift.common.data.model.domain.req;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftExtensionParam {
    private String tradeId;
    private String productOrderId;
    private String ledgerId;
    private String expiryDate;
    private String giftNo;
}
