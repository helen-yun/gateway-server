package com.pongift.common.service;

import com.pongift.common.data.model.domain.dto.Gift;
import com.pongift.common.data.model.domain.dto.ResendGiftCard;
import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.common.data.model.domain.req.OrderListParam;
import com.pongift.common.data.model.domain.res.ApiOutput;
import com.pongift.common.utils.ApiClient;
import com.pongift.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by dplee on 2020/03/18.
 */
@Slf4j
@Service
public class ApprovedService {
    @Value("${platfos.api.url}")
    private String API_PLATFOS_URL;

    /**
     * 상품판매 승인 요청
     */
    public boolean sendApproved(Trade param) {
        boolean success = false;
        try {
            ApiClient client = new ApiClient();

            String appReqUrl = API_PLATFOS_URL + "/trade/approved/request";
            String requestJson = JsonUtil.objectToStr(param);
            String apiResult = client.pongiftplus("POST", appReqUrl, requestJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                success = true;
            }
            log.info("TradeService.sendApproved:" + apiOut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message:" + e.getMessage());
        }
        return success;
    }

    /**
     * 상품판매 승인 취소요청
     */
    public boolean sendCancel(Trade param) {
        boolean success = false;
        try {
            ApiClient client = new ApiClient();
            String cancelUrl = API_PLATFOS_URL + "/trade/approved/cancel";
            String cancelJson = JsonUtil.objectToStr(param);
            String apiResult = client.pongiftplus("POST", cancelUrl, cancelJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                success = true;
            }
            log.info("TradeService.sendCancel:" + apiOut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message:" + e.getMessage());
        }
        return success;
    }

    /**
     * 상품 반품 승인
     */
    public boolean sendReturn(Trade param) {
        boolean success = false;
        try {
            ApiClient client = new ApiClient();
            String returnUrl = API_PLATFOS_URL + "/trade/approved/return";
            String returnJson = JsonUtil.objectToStr(param);
            String apiResult = client.pongiftplus("POST", returnUrl, returnJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                success = true;
            }
            log.info("==================================================");
            log.info("TradeService.sendReturn : {}", apiOut);
            log.info("//================================================");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message : {}", e.getMessage());
        }
        return success;
    }

    /**
     * 선물하기 상품판매 승인 요청
     */
    public Gift sendApprovedGift(Trade param) {
        Gift rtn = new Gift();
        rtn.setSuccess(false);

        try {
            ApiClient client = new ApiClient();

            String appReqUrl = API_PLATFOS_URL + "/trade/approved/gift";
            String requestJson = JsonUtil.objectToStr(param);
            String apiResult = client.pongiftplus("POST", appReqUrl, requestJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                rtn.setSuccess(true);
                rtn.setBarcodeNo(String.valueOf(apiOut.getList().get(0).get("barcodeNo")));
                rtn.setProductOrderId(String.valueOf(apiOut.getList().get(0).get("productOrderId")));
                rtn.setExpiryDate(String.valueOf(apiOut.getList().get(0).get("expiryDate")));

            }
            log.info("TradeService.sendApprovedGift:" + apiOut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message:" + e.getMessage());
        }
        return rtn;
    }

    /**
     * 상품권 재발송 요청
     */
    public boolean sendResend(Trade trade) {
        boolean success = false;
        ResendGiftCard resendGiftCard = new ResendGiftCard();
        resendGiftCard.setSendTalkSt("Y");
        resendGiftCard.setOrderNo(trade.getTradeId());
        resendGiftCard.setTradeTp(trade.getTradeTp());
        resendGiftCard.setChannelGb(trade.getChannelGb());

        try {
            ApiClient client = new ApiClient();
            String appReqUrl = API_PLATFOS_URL + "/giftcard/resend";
            String requestJson = JsonUtil.objectToStr(resendGiftCard);
            String apiResult = client.pongiftplus("POST", appReqUrl, requestJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                success = true;
            }
            log.info("TradeService.sendResend 재발송 :" + apiOut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message:" + e.getMessage());
        }
        return success;
    }

    /**
     * 반품 요청 거래내역 전송
     *
     * @param param
     * @return
     */
    public boolean sendReturnRequestTrade(Trade param) {
        boolean success = false;
        try {
            ApiClient client = new ApiClient();

            String appReqUrl = API_PLATFOS_URL + "/trade/request/return";
            String requestJson = JsonUtil.objectToStr(param);
            String apiResult = client.pongiftplus("POST", appReqUrl, requestJson);
            ApiOutput apiOut = JsonUtil.getApiOutput(apiResult);
            if ("0001".equals(apiOut.getResponse().getCode())) { // 성공
                success = true;
            }
            log.info("TradeService.sendTrade:" + apiOut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("## Exception message:" + e.getMessage());
        }
        return success;
    }

}
