package com.pongift.marketplace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pongift.common.code.ApiMessageCode;
import com.pongift.common.data.model.domain.req.GiftExtensionParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.utils.ApiClient;
import com.pongift.common.utils.JsonUtil;
import com.pongift.marketplace.data.req.GoodsReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsService {
    @Value("${platfos.marketplace.url}")
    private String MARKETPLACE_API_URL;

    /**
     * 상품 조회
     * @param goodsId
     * @return
     */
    public ResOutput selectGoods(String goodsId) {
        ResOutput resOutput = new ResOutput();
        ApiClient apiClient = new ApiClient();
        GoodsReq goodsReq;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String result = apiClient.marketPlace("GET", MARKETPLACE_API_URL + "/internal/goods?goodsId=" + goodsId, null );
            goodsReq = objectMapper.readValue(result, GoodsReq.class);
            resOutput.setData(goodsReq);
        } catch (Exception e) {
            log.error("제휴몰 상품 조회 실패 : {}", e.getMessage());
            resOutput.setResponse(new Response(false, e.getMessage()));
        }
        return resOutput;
    }

    /**
     * 상품 등록
     * @param goodsReq
     * @return
     */
    public ResOutput insertGoods(GoodsReq goodsReq) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(goodsReq);
        ApiClient apiClient = new ApiClient();
        String result;
        try {
            result = apiClient.marketPlace("POST", MARKETPLACE_API_URL + "/internal/goods", json);
            resOutput.setData(result);
            log.info(result);
        } catch (Exception e) {
            log.error("제휴몰 상품 등록 실패 : {}", e.getMessage());
            resOutput.setResponse(new Response(false, e.getMessage()));
        }
            return resOutput;

    }

    /**
     * 상품 수정
     * @param goodsReq
     * @return
     */
    public ResOutput updateGoods(GoodsReq goodsReq) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(goodsReq);
        ApiClient apiClient = new ApiClient();
        try {
            String result = apiClient.marketPlace("PUT", MARKETPLACE_API_URL + "/internal/goods", json);
            log.info(result);
        } catch (Exception e) {
            log.error("제휴몰 상품 수정 실패 : {}", e.getMessage());
            resOutput.setResponse(new Response(false, e.getMessage()));
        }
        return resOutput;
    }


    /**
     * 상품 판매상태 변경
     * @param channelId
     * @return
     */
    public ResOutput updateSaleStatus(String channelId) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(channelId);
        ApiClient apiClient = new ApiClient();
        try {
            String result = apiClient.marketPlace("PUT", MARKETPLACE_API_URL + "/internal/goods/exhibits?goodsId=" + channelId, json);
            resOutput.setResponse(new Response(true, ApiMessageCode.API_MSG_0001.getValue()));
            log.info(result);
        } catch (Exception e) {
            log.error("제휴몰 판매 상태 변경 실패 : {}", e.getMessage());
            resOutput.setResponse(new Response(false, e.getMessage()));
        }
        return resOutput;
    }
}
