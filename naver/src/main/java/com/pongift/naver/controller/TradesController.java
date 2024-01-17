package com.pongift.naver.controller;

import com.pongift.common.data.model.domain.dto.Gift;
import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.req.OrderInfoParam;
import com.pongift.common.data.model.domain.req.TradeCancel;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.error.ApiException;
import com.pongift.naver.service.ClaimService;
import com.pongift.naver.service.TradeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradesController {
    private final TradeService tradeService;
    private final ClaimService claimService;

    /*==============================================커머스 API==============================================*/
    @ApiOperation(value = "네이버 주문 취소 요청(커머스 API)")
    @PostMapping("/cancelSale")
    public ResponseEntity<Object> cancelRequest(@RequestBody TradeCancel tradeCancel) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 주문 취소 요청");
        log.debug("param: {}", tradeCancel);
        log.debug("//================================================");
        Response response = claimService.cancelRequest(tradeCancel);

        ResOutput resOutput = new ResOutput();
        resOutput.setResponse(response);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "반품 승인 처리(커머스 API)")
    @PostMapping("/request/return")
    public ResponseEntity<Object> approveReturnTrade(@RequestBody OrderInfoParam param) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 반품 승인 처리");
        log.debug("param : {}", param);
        log.debug("//================================================");
        Response result = claimService.approveReturnTrade(param.getProductOrderId());

        ResOutput resOutput = new ResOutput();
        resOutput.setResponse(result);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "거래상세(커머스 API)")
    @GetMapping("")
    public ResponseEntity<Object> getTradeDetail(@RequestParam("productOrderId") String productOrderId) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 거래상세");
        log.debug("productOrderId : {}", productOrderId);
        log.debug("//================================================");
        Trade trade = tradeService.getOrderInfo(productOrderId);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(trade);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO : 샌드박스 테스트
    @ApiOperation(value = "e-coupon 사용 처리 요청(커머스 API)")
    @PostMapping("/usage/request")
    public ResponseEntity<Object> postUsageRequest(@RequestParam("productOrderId") String productOrderId) {
        log.debug("==================================================");
        log.debug("네이버 커머스: e-coupon 사용 처리 요청");
        log.debug("productOrderId : {}", productOrderId);
        log.debug("//================================================");
        if (StringUtils.isEmpty(productOrderId)) {
            throw new ApiException(false, "ProductOrderId is required");
        }
        Response response = tradeService.changeECouponStatus(productOrderId);

        ResOutput resOutput = new ResOutput();
        resOutput.setResponse(response);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO : 샌드박스 테스트
    @ApiOperation(value = "선물 유효기간 연장(커머스 API)")
    @GetMapping("/gift/extension")
    public ResponseEntity<Object> getTradeGiftExtension(@ApiIgnore Gift param) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 선물 유효기간 연장");
        log.debug("param : {}", param);
        log.debug("//================================================");
        if (StringUtils.isEmpty(param.getProductOrderId())) {
            throw new ApiException(false, "ProductOrderId is required");
        }
        if (StringUtils.isEmpty(param.getExpiryDate())) {
            throw new ApiException(false, "ExpiryDate is required");
        }
        Response response = tradeService.updateGiftExtension(param);

        ResOutput resOutput = new ResOutput();
        resOutput.setResponse(response);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
