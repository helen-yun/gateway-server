package com.pongift.marketplace.service;

import com.pongift.common.data.model.domain.req.GiftExtensionParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.utils.ApiClient;
import com.pongift.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GiftCardService {
    @Value("${platfos.marketplace.url}")
    private String MARKETPLACE_API_URL;

    /**
     * 상품권 유효기간 연장
     *
     * @param giftExtensionParam
     * @return
     */
    public ResOutput extendGiftCardExpirationDate(GiftExtensionParam giftExtensionParam) {
        ResOutput resOutput = new ResOutput();
        String json = JsonUtil.objectToStr(giftExtensionParam);
        ApiClient apiClient = new ApiClient();
        try {
            String result = apiClient.marketPlace("POST", MARKETPLACE_API_URL + "/internal/giftcard/extension", json);
            log.info(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resOutput.setResponse(new Response());
        return resOutput;
    }
}
