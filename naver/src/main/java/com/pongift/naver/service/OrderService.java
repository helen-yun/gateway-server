package com.pongift.naver.service;

import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.code.ChangedType;
import com.pongift.naver.commerce.TokenService;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.CommerceDataUtils;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;
import com.pongift.naver.data.model.res.ChangedOrdersRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final TokenService tokenService;
    private final CommerceWebClient commerceWebClient;
    private final TradeService tradeService;

    /**
     * 결제 완료 목록 조회
     * @return
     */
    public ChangedOrdersRes getOrderList() {
        ChangedOrdersRes orderRes = commerceWebClient.getChangedOrderList(tokenService.getAccessToken(),ChangedType.PAYED.getValue(), CommerceDataUtils.customIsoEncodeTime());
        log.info("SUCCESS 결제 목록 조회 : {}", JsonUtil.objectToStr(orderRes));

        return orderRes;
    }

    /**
     * 결제 완료 상세 조회
     * @param changedOrdersRes
     * @return
     */
    public ChangedOrderInfoRes getOrderDetail(ChangedOrdersRes changedOrdersRes){
        //주문 목록
        ChangedOrderInfoRes result = new ChangedOrderInfoRes();
        List<ChangedOrderInfoRes.OrderData> list = new ArrayList<>();

        for (ChangedOrdersRes.LastChangeStatuses item :  changedOrdersRes.getData().getLastChangeStatuses()) {
            if (StringUtils.isEmpty(item.getGiftReceivingStatus())
                    && item.getProductOrderStatus().equals(ChangedType.PAYED.getValue())) {
                //상세 조회
                ChangedOrderInfoRes changedOrderInfoRes = commerceWebClient.getChangedOrderInfo(tokenService.getAccessToken(), item);
                list.add(changedOrderInfoRes.getData().get(0));
            }
        }
        result.setData(list);
        log.info("SUCCESS 결제완료 주문 상세 정보 : {}", JsonUtil.objectToStr(list));

        return result;
    }

    /**
     * 선물 상세 조회
     * @param changedOrdersRes
     * @return
     */
    public ChangedOrderInfoRes getGiftOrderDetail(ChangedOrdersRes changedOrdersRes, String changedType){
        //주문 목록
        ChangedOrderInfoRes result = new ChangedOrderInfoRes();
        List<ChangedOrderInfoRes.OrderData> list = new ArrayList<>();

        for (ChangedOrdersRes.LastChangeStatuses item :  changedOrdersRes.getData().getLastChangeStatuses()) {
            if (StringUtils.isNotEmpty(item.getGiftReceivingStatus())
                    && item.getGiftReceivingStatus().equals(ChangedType.RECEIVED.getValue())
                    && item.getProductOrderStatus().equals(changedType))  {
                //상세 조회
                ChangedOrderInfoRes changedOrderInfoRes = commerceWebClient.getChangedOrderInfo(tokenService.getAccessToken(), item);
                list.add(changedOrderInfoRes.getData().get(0));
            }
        }
        result.setData(list);
        log.info("SUCCESS 선물 상세 정보 : {}",  JsonUtil.objectToStr(list));

        return result;
    }

    /**
     * 주문 승인 처리
     * @param orderList
     */
    public void approveOrder(List<Trade> orderList){
        for (Trade order : orderList) {
            tradeService.runApprove(order);
        }
    }


    /**
     * 취소 완료 처리
     */
    public void approveCancelGift(List<Trade> orderList) {
        if (orderList != null) {
            for (Trade order : orderList) {
                tradeService.runCancel(order);
            }
        }
    }

    /**
     * 선물 > 결제 완료 처리
     */
    public void approveGift(List<Trade> giftPayedList) {
        if (giftPayedList != null) {
            for (Trade order : giftPayedList) {
                tradeService.runApproveGift(order);
            }
        }
    }
}
