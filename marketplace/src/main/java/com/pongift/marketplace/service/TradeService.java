package com.pongift.marketplace.service;

import com.pongift.common.data.model.domain.req.UsageParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.utils.ApiClient;
import com.pongift.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TradeService {
    @Value("${platfos.marketplace.url}")
    private String MARKETPLACE_API_URL;

    /**
     * 상품권 사용 처리
     *
     * @param usageParam
     * @return ResOutput
     */
    public ResOutput useGiftCard(UsageParam usageParam) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(usageParam);
        ApiClient apiClient = new ApiClient();
        try {
            String result = apiClient.marketPlace("POST", MARKETPLACE_API_URL + "/internal/giftcard/use", json);
            log.info(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resOutput.setResponse(new Response());
        return resOutput;
    }

    /**
     * 상품권 사용 취소 처리
     *
     * @param usageParam
     * @return ResOutput
     */
    public ResOutput cancelGiftCard(UsageParam usageParam) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(usageParam);
        ApiClient apiClient = new ApiClient();
        try {
            String result = apiClient.marketPlace("POST", MARKETPLACE_API_URL + "/internal/giftcard/cancel", json);
            log.info(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resOutput.setResponse(new Response());
        return resOutput;
    }
}
