package com.pongift.marketplace.controller;

import com.pongift.common.data.model.domain.req.GiftExtensionParam;
import com.pongift.common.data.model.domain.req.UsageParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.error.ApiException;
import com.pongift.marketplace.service.GiftCardService;
import com.pongift.marketplace.service.TradeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gift")
public class GiftCardController {
    private final GiftCardService giftCardService;

    /**
     * 상품권 유효기간 연장
     *
     * @param giftExtensionParam
     * @return
     */
    @ApiOperation(value = "상품권 유효기간 연장")
    @PostMapping("/extension")
    public ResponseEntity<ResOutput> extendGiftCardExpirationDate(@RequestBody GiftExtensionParam giftExtensionParam) {
        if (!StringUtils.hasText(giftExtensionParam.getGiftNo())) {
            throw new ApiException(false, "giftNo is required");
        }
        ResOutput resOutput = giftCardService.extendGiftCardExpirationDate(giftExtensionParam);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
