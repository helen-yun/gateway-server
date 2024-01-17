package com.pongift.naver.service;

import com.pongift.common.ApiConstants;
import com.pongift.common.code.DeliveryMethodCd;
import com.pongift.common.code.TradeTp;
import com.pongift.common.data.model.domain.dto.Gift;
import com.pongift.common.data.model.domain.dto.Good;
import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.service.ApprovedService;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.code.ECouponType;
import com.pongift.naver.commerce.TokenService;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.ProductOrderInfoToTrade;
import com.pongift.naver.data.model.req.DispatchProductReq;
import com.pongift.naver.data.model.req.ECouponReq;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;
import com.pongift.naver.data.model.res.ChangedOrdersRes;
import com.pongift.naver.data.model.res.ProductOrderRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pongift.naver.data.CommerceDataUtils.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class TradeService {
    private final ApprovedService approvedService;
    private final CommerceWebClient commerceWebClient;
    private final TokenService tokenService;

    /*==============================================커머스 API==============================================*/
    /**
     * 리턴값 맵핑
     * @param changedOrderInfoRes 상품 상세 정보
     * @return
     */
    public List<Trade> mappingOrderData(ChangedOrderInfoRes changedOrderInfoRes){
        List<Trade> tradeList = new ArrayList<>();

        //결과값 맵핑
        for(ChangedOrderInfoRes.OrderData info : changedOrderInfoRes.getData()){
            Trade trade = new ProductOrderInfoToTrade().getTradeMapping(info);
            tradeList.add(trade);
        }

        log.info("SUCCESS 거래 상세 리턴값 맵핑 : {} ",JsonUtil.objectToStr(tradeList));
        return tradeList;
    }

    /**
     * 거래 상세 조회 (네이버 커머스 API)
     * @param param productOrderId 상품 거래 ID
     * @return
     */
    public Trade getOrderInfo(String param) {
        Trade trade = new Trade();
        //요청값 맵핑
        ChangedOrdersRes.LastChangeStatuses lastChangeStatuses = new ChangedOrdersRes.LastChangeStatuses();
        lastChangeStatuses.setProductOrderId(param);

        //커머스 API 요청(거래 상세 조회)
        ChangedOrderInfoRes orderRes = commerceWebClient.getChangedOrderInfo(tokenService.getAccessToken(), lastChangeStatuses);
        if(orderRes.getData() == null) return trade;

        //결과값 맵핑
        log.info("SUCCESS 거래 상세 조회 : {} ", JsonUtil.objectToStr(orderRes));
        for(ChangedOrderInfoRes.OrderData info : orderRes.getData()){
            trade = new ProductOrderInfoToTrade().getTradeMapping(info);
        }


        return trade;
    }

    /**
     * 반품 승인 건 전송
     */
    public void sendReturnApprovedList(List<Trade> returnedList){
        if (returnedList != null) {
            for (Trade trade : returnedList) {
                trade.setTradeTp(TradeTp.RETURN.getCode());
                boolean success = approvedService.sendReturn(trade);
                log.info("반품 승인 처리 response : {}", success);
            }
        }
    }

    /**
     * E-Coupon 사용 처리
     * @param param productOrderId 상품 주문 번호
     * @return
     */
    public Response changeECouponStatus(String param) {
        Response result = new Response();

        ECouponReq.ECouponDetail detail = new ECouponReq.ECouponDetail();
        ECouponReq request = new ECouponReq();

        detail.setProductOrderId(param);
        detail.setECouponNo(param);
        detail.setECouponStatusType(ECouponType.USED.getValue());
        detail.setUsedDate(customIsoTime());
        request.setData(Collections.singletonList(detail));
        log.info("ECouponReq: {}", JsonUtil.objectToStr(request));

        //E-Coupon 사용 처리(커머스 API 요청)
        ProductOrderRes response = commerceWebClient.changeECouponStatus(tokenService.getAccessToken(), request);
        if(response.getData() == null) return new Response(false, null);
        log.info("E-Coupon 사용 처리 response : {}", JsonUtil.objectToStr(response));

        //성공 데이터 미존재
        if(response.getData().getFailProductOrderInfos().size() > 0) {
            ProductOrderRes.OrderData.FailProductOrderInfos failRes = response.getData().getFailProductOrderInfos().get(0);
            result = new Response(false, failRes.getCode() + failRes.getMessage());
        }

        return result;
    }

    /**
     * E-Coupon 유효기간 연장
     * @param param
     * @return
     */
    public Response updateGiftExtension(Gift param) {
        ECouponReq request = new ECouponReq();
        ECouponReq.ECouponDetail detail = new ECouponReq.ECouponDetail();
        Response response = new Response();

        detail.setProductOrderId(param.getProductOrderId());
        detail.setECouponNo(param.getProductOrderId());
        detail.setValidStartDate(customIsoTime());
        detail.setValidEndDate(toOffsetDateFormat(param.getExpiryDate() + "T23:59:59.000+09:00"));
        request.setData(Collections.singletonList(detail));
        log.info("ECouponReq: {}", JsonUtil.objectToStr(param));

        //유효기간 연장(커머스 API 요청)
        ProductOrderRes productOrderRes = commerceWebClient.eCouponValidDateChange(tokenService.getAccessToken(), request);
        if(productOrderRes.getData() == null) return new Response(false, null);
        log.info("E-Coupon 유효기간 연장 response : {}", JsonUtil.objectToStr(response));

        //성공 데이터 미존재
        if(productOrderRes.getData().getFailProductOrderInfos().size() > 0) {
            ProductOrderRes.OrderData.FailProductOrderInfos failRes = productOrderRes.getData().getFailProductOrderInfos().get(0);
            response = new Response(false, failRes.getCode() + failRes.getMessage());
        }

        return response;
    }

    /**
     * 주문 상태 변경된 거래내역 전송 (GateWay > API)
     *
     * @param orderList
     * @return resultCount
     */
    public int sendChangedProductOrderList(List<Trade> orderList) {
        int resultCount = 0;
        if (orderList != null) {
            for (Trade trade : orderList) {
                boolean sendResult = approvedService.sendReturnRequestTrade(trade);
                if (sendResult) resultCount++;
            }
        }
        return resultCount;
    }


    /**
     * 주문 승인 및 발송 처리
     */
    public boolean runApprove(Trade trade) {
        trade.setTradeTp(TradeTp.SALES.getCode()); // 판매

        log.info("#########  TradeService > runApprove #########");
        // 1. 주문처리 > pongift
        trade.setChannelGb(ApiConstants.NAVER);
        boolean success = approvedService.sendApproved(trade);
        log.info("## runApprove > sendApproved > pongift:" + success);
        if (success) {
            // 2. 발송처리
            for (Good good : trade.getGoodsList()) {
                success = this.shipProductOrder(good.getProductOrderId());
                log.info("## 2. 발송처리 : ShipProductOrder:" + success);
            }
        }

        return success;
    }

    /**
     * 주문 취소 처리
     */
    public boolean runCancel(Trade trade) {
        trade.setTradeTp(TradeTp.SALES_CANCEL.getCode()); // 판매취소
        log.info("#########  TradeService > runCancel #########");
        boolean success = approvedService.sendCancel(trade);
        log.info("## 2-1. 주문취소:" + success);
        return success;
    }

    /**
     * 발송 처리 (네이버 커머스 API)
     * @param productOrderId 상품 주문 번호
     * @return
     */
    public boolean shipProductOrder(String productOrderId) {
        boolean success = false;

        //request 셋팅
        DispatchProductReq dispatchProductReq = new DispatchProductReq();
        DispatchProductReq.DispatchProductOrders dispatchProductOrders = new DispatchProductReq.DispatchProductOrders();
        dispatchProductOrders.setProductOrderId(productOrderId);
        dispatchProductOrders.setDeliveryMethod(DeliveryMethodCd.NOTHING.getValue());
        dispatchProductOrders.setDispatchDate(customIsoTime());
        dispatchProductReq.setDispatchProductOrders(Collections.singletonList(dispatchProductOrders));
        log.info("DispatchProductReq: {}", JsonUtil.objectToStr(dispatchProductReq));

        //발송 처리(커머스 API 요청)
        ProductOrderRes response = commerceWebClient.shipProductOrder(tokenService.getAccessToken(), dispatchProductReq);
        if(response.getData() == null) return false;
        log.info("발송 처리 response : {}", JsonUtil.objectToStr(response));

        //성공 데이터값이 존재
        if (response.getData().getSuccessProductOrderIds().size() > 0) {
            success = true;
        }

        return success;
    }


    /**
     * 선물하기 거래승인
     */
    public boolean runApproveGift(Trade trade) {
        log.info("#########  TradeService > runApproveGift #########");
        boolean success = false;
        trade.setTradeTp(TradeTp.SALES.getCode()); // 판매

        // 1. 주문처리 > pongift
        trade.setChannelGb(ApiConstants.NAVER);
        Gift gift = approvedService.sendApprovedGift(trade);
        log.info("## 1. 주문처리 : runApproveGift > sendApprovedGift > pongift : " + gift);

        if (gift.isSuccess()) {
            // 2. 발송처리
            for (Good good : trade.getGoodsList()) {
                success = this.shipProductOrderGift(good.getProductOrderId(), good.getProductOrderId(), gift.getBarcodeNo(), gift.getExpiryDate());
                log.info("## 2. 발송처리 : ShipProductOrderGift:" + success);
            }
        }
        return success;
    }

    /**
     * E-Coupon 발송 처리
     * @param productOrderId 상품 주문 번호
     * @param eCouponNo E쿠폰 번호
     * @param barcodeNo 바코드 번호
     * @param validEndDate 유효기간
     * @return
     */
    public boolean shipProductOrderGift(String productOrderId, String eCouponNo, String barcodeNo, String validEndDate) {
        ECouponReq request = new ECouponReq();
        ECouponReq.ECouponDetail detail = new ECouponReq.ECouponDetail();
        boolean result = false;

        detail.setProductOrderId(productOrderId);
        detail.setBarcodeNos(Collections.singletonList(barcodeNo));
        detail.setECouponNo(eCouponNo);
        detail.setValidStartDate(customIsoTime());
        detail.setValidEndDate(toOffsetDateFormat(validEndDate + "T23:59:59.000+09:00"));
        request.setData(Collections.singletonList(detail));
        log.info("ECouponReq: {}", JsonUtil.objectToStr(request));

        //발송 처리(커머스 API 요청)
        ProductOrderRes response = commerceWebClient.eCouponDispatch(tokenService.getAccessToken(), request);
        if(response.getData() == null) return false;
        log.info("E-Coupon 발송 처리 response : {}", response);

        //성공 데이터값이 존재
        if (response.getData().getSuccessProductOrderIds().size() > 0) {
            result = true;
        }

        return result;
    }
}
