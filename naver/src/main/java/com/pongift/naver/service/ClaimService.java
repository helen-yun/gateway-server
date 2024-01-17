package com.pongift.naver.service;

import com.pongift.common.code.CancelSaleReasonCd;
import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.req.TradeCancel;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.commerce.TokenService;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.CommerceDataUtils;
import com.pongift.naver.data.model.req.RejectOrderReq;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;
import com.pongift.naver.data.model.res.ChangedOrdersRes;
import com.pongift.naver.data.model.res.ProductOrderRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClaimService {
    private final TokenService tokenService;
    private final CommerceWebClient commerceWebClient;
    private final TradeService tradeService;

    /**
     * 취소 완료 목록 조회
     * @return
     */
    public ChangedOrdersRes getClaimList(String claimType) {
        ChangedOrdersRes orderRes = commerceWebClient.getChangedOrderList(tokenService.getAccessToken(), claimType, CommerceDataUtils.customIsoEncodeTime());
        log.info("SUCCESS 클레임 목록 조회 : {}", JsonUtil.objectToStr(orderRes));

        return orderRes;
    }

    /**
     * 클레임 상세 조회
     * @param changedOrdersRes
     * @return
     */
    public ChangedOrderInfoRes getClaimDetail(ChangedOrdersRes changedOrdersRes, String changedType){
        ChangedOrderInfoRes result = new ChangedOrderInfoRes();
        List<ChangedOrderInfoRes.OrderData> list = new ArrayList<>();

        for (ChangedOrdersRes.LastChangeStatuses item :  changedOrdersRes.getData().getLastChangeStatuses()) {
            if (StringUtils.isEmpty(item.getGiftReceivingStatus()) && item.getClaimStatus().equals(changedType)) {
                //상세 조회
                ChangedOrderInfoRes changedOrderInfoRes = commerceWebClient.getChangedOrderInfo(tokenService.getAccessToken(), item);
                list.add(changedOrderInfoRes.getData().get(0));
            }
        }

        result.setData(list);
        log.info("SUCCESS 클레임 주문 상세 정보 : {}", JsonUtil.objectToStr(list));

        return result;
    }

    /**
     * 주문 취소 처리
     * @param claimList
     */
    public void cancelApprove(List<Trade> claimList){
        for (Trade claim : claimList) {
            tradeService.runCancel(claim);
        }
    }

    /**
     * 반품 승인 (네이버 커머스 API)
     *
     * @param param 상품 주문 번호
     */
    public Response approveReturnTrade(String param) {
        Response result = new Response();

        //반품 승인(커머스 API 요청)
        ProductOrderRes productOrderRes = commerceWebClient.returnApprove(tokenService.getAccessToken(), param);
        if(productOrderRes.getData() == null) return new Response(false, null);
        log.info("반품 승인 response : {}", JsonUtil.objectToStr(productOrderRes));

        //성공 데이터 미존재
        if(productOrderRes.getData().getFailProductOrderInfos().size() > 0) {
            ProductOrderRes.OrderData.FailProductOrderInfos failRes = productOrderRes.getData().getFailProductOrderInfos().get(0);
            result = new Response(false, failRes.getCode() + failRes.getMessage());
        }

        return result;
    }


    /**
     * 취소 요청
     * @param param 취소 요청 데이터
     * @return
     */
    public Response cancelRequest(TradeCancel param) {
        Response result = new Response();
        String reason = this.getClaimRequestReasonType(param.getCancelSaleReasonCd());

        //취소 요청(커머스 API 요청)
        ProductOrderRes productOrderRes = commerceWebClient.cancelOrderRequest(tokenService.getAccessToken(), param, reason);
        if(productOrderRes.getData() == null) return new Response(false, null);
        log.info("취소 요청 response : {}", JsonUtil.objectToStr(productOrderRes));

        //성공 데이터 미존재
        if(productOrderRes.getData().getFailProductOrderInfos().size() > 0) {
            ProductOrderRes.OrderData.FailProductOrderInfos failRes = productOrderRes.getData().getFailProductOrderInfos().get(0);
            result = new Response(false, failRes.getCode() + failRes.getMessage());
        }

        return result;
    }

    /**
     * 반품거부(철회)
     * @param rejectOrderReq 거부 요청값
     * @return
     */
    public Response rejectReturnRequest (RejectOrderReq rejectOrderReq) {
        Response result = new Response();

        //거부 요청(커머스 API 요청)
        ProductOrderRes productOrderRes = commerceWebClient.rejectReturnRequest(tokenService.getAccessToken(), rejectOrderReq);
        if(productOrderRes.getData() == null) return new Response(false, null);
        log.info("반품 거부 response : {}", JsonUtil.objectToStr(productOrderRes));

        //성공 데이터 미존재
        if(productOrderRes.getData().getFailProductOrderInfos().size() > 0) {
            ProductOrderRes.OrderData.FailProductOrderInfos failRes = productOrderRes.getData().getFailProductOrderInfos().get(0);
            result = new Response(false, failRes.getCode() + failRes.getMessage());
        }

        return result;
    }


    //TODO: 추후 테스트 후 재정리 필요
    private String getClaimRequestReasonType(CancelSaleReasonCd cancelSaleReasonCd) {
        switch (cancelSaleReasonCd) {
            case SOLD_OUT:
                return "SOLD_OUT";
            default:
                return "INTENT_CHANGED";
        }
    }
}
