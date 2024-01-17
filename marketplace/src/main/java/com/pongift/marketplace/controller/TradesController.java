package com.pongift.marketplace.controller;

import com.pongift.common.data.model.domain.req.UsageParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.error.ApiException;
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
@RequestMapping("/trade")
public class TradesController {
    private final TradeService tradeService;

    /**
     * 상품권 사용 처리
     *
     * @param usageParam
     * @return
     */
    @ApiOperation(value = "상품권 사용 처리")
    @PostMapping("/usage/request")
    public ResponseEntity<ResOutput> useGiftCard(@RequestBody UsageParam usageParam) {
        if (!StringUtils.hasText(usageParam.getGiftNo())) {
            throw new ApiException(false, "giftNo is required");
        }
        ResOutput resOutput = tradeService.useGiftCard(usageParam);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    /**
     * 상품권 사용 취소 처리
     *
     * @param usageParam
     * @return
     */
    @ApiOperation(value = "상품권 사용 취소 처리")
    @PostMapping("/cancel/request")
    public ResponseEntity<ResOutput> cancelGiftCard(@RequestBody UsageParam usageParam) {
        if (!StringUtils.hasText(usageParam.getGiftNo())) {
            throw new ApiException(false, "giftNo is required");
        }
        ResOutput resOutput = tradeService.cancelGiftCard(usageParam);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
