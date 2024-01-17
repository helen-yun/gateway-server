package com.pongift.naver.controller;

import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.naver.code.ChangedType;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;
import com.pongift.naver.data.model.res.ChangedOrdersRes;
import com.pongift.naver.service.OrderService;
import com.pongift.naver.service.TradeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final TradeService tradeService;

    @ApiOperation(value = "주문 승인처리(커머스 API)")
    @GetMapping("/approved")
    public ResponseEntity<Object> approveOrder() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 거래목록 주문 승인처리");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = orderService.getOrderList();
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = orderService.getOrderDetail(changedOrdersRes);
        List<Trade> orderList = tradeService.mappingOrderData(changedOrderInfoRes);
        orderService.approveOrder(orderList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(orderList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO : 샌드박스 테스트
    @ApiOperation(value = "선물 > 주문 완료 > 정상 완료 처리(커머스 API)")
    @GetMapping("/approved/gift")
    public ResponseEntity<Object> approveOrderGift() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 선물 > 주문 완료 및 발송 처리");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = orderService.getOrderList();
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = orderService.getGiftOrderDetail(changedOrdersRes, ChangedType.PAYED.getValue());
        List<Trade> cancelGiftList = tradeService.mappingOrderData(changedOrderInfoRes);
        orderService.approveGift(cancelGiftList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(cancelGiftList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO : 샌드박스 테스트
    @ApiOperation(value = "선물 > 주문 완료 > 취소 완료 처리(커머스 API)")
    @GetMapping("/approved/cancel/gift")
    public ResponseEntity<Object> approveCancelGift() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 선물 > 취소 완료 처리");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = orderService.getOrderList();
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = orderService.getGiftOrderDetail(changedOrdersRes, ChangedType.RETURN_DONE.getValue());
        List<Trade> returnGiftList = tradeService.mappingOrderData(changedOrderInfoRes);
        orderService.approveCancelGift(returnGiftList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(returnGiftList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO : 샌드박스 테스트
    @ApiOperation(value = "선물 > 주문 완료 > 환불 완료 처리(커머스 API)")
    @GetMapping("/approved/return/gift")
    public ResponseEntity<Object> approveReturnGift() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 선물 > 환불 완료 처리");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = orderService.getOrderList();
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = orderService.getGiftOrderDetail(changedOrdersRes, ChangedType.RETURN_DONE.getValue());
        List<Trade> returnGiftList = tradeService.mappingOrderData(changedOrderInfoRes);
        orderService.approveCancelGift(returnGiftList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(returnGiftList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
