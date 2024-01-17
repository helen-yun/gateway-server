package com.pongift.naver.controller;

import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.code.ChangedType;
import com.pongift.naver.data.model.req.RejectOrderReq;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;
import com.pongift.naver.data.model.res.ChangedOrdersRes;
import com.pongift.naver.service.ClaimService;
import com.pongift.naver.service.TradeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/claim")
public class ClaimController {

    private final ClaimService claimService;
    private final TradeService tradeService;

    @ApiOperation(value = "네이버 반품 요청 건 거래목록 조회(커머스 API)")
    @GetMapping("/list/return")
    public ResponseEntity<Object> getReturnRequestedList() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 반품 요청 거래 목록 조회");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = claimService.getClaimList(ChangedType.CLAIM_REQUESTED.getValue());
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = claimService.getClaimDetail(changedOrdersRes, ChangedType.RETURN_REQUEST.getValue());
        List<Trade> claimList = tradeService.mappingOrderData(changedOrderInfoRes);
        tradeService.sendChangedProductOrderList(claimList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(claimList);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "주문 취소 건 전송(커머스 API)")
    @GetMapping("/approved/cancel")
    public ResponseEntity<Object> approveClaim() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 주문 취소 건 전송");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = claimService.getClaimList(ChangedType.CLAIM_COMPLETED.getValue());
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = claimService.getClaimDetail(changedOrdersRes, ChangedType.CANCEL_DONE.getValue());
        List<Trade> cancelList = tradeService.mappingOrderData(changedOrderInfoRes);
        claimService.cancelApprove(cancelList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(cancelList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "반품 거부(철회)(커머스 API)")
    @PostMapping("/reject/return")
    public ResponseEntity<Object> sendReturnReject(@RequestBody RejectOrderReq param){
        log.debug("==================================================");
        log.debug("네이버 커머스: 반품 거부(철회)");
        log.debug("Param : {}", JsonUtil.objectToStr(param));
        log.debug("//================================================");

        Response response = claimService.rejectReturnRequest(param);

        ResOutput resOutput = new ResOutput();
        resOutput.setResponse(response);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    //TODO: 사용 여부 검토 필요
    @ApiOperation(value = "네이버 반품 승인 건 전송(커머스 API)")
    @GetMapping("/approved/return")
    public ResponseEntity<Object> sendReturnApproveList() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 반품 승인 건 전송");
        log.debug("//================================================");
        ChangedOrdersRes changedOrdersRes = claimService.getClaimList(ChangedType.CLAIM_COMPLETED.getValue());
        if(changedOrdersRes.getData() == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ChangedOrderInfoRes changedOrderInfoRes = claimService.getClaimDetail(changedOrdersRes, ChangedType.RETURN_DONE.getValue());
        List<Trade> returnedList = tradeService.mappingOrderData(changedOrderInfoRes);
        tradeService.sendReturnApprovedList(returnedList);

        ResOutput resOutput = new ResOutput();
        resOutput.setList(returnedList);
        resOutput.setResponse(new Response());

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
