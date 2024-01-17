package com.pongift.marketplace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pongift.common.data.model.domain.dto.Category;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.utils.ApiClient;
import com.pongift.marketplace.data.req.GoodsReq;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CategoryService {
    @Value("${platfos.marketplace.url}")
    private String MARKETPLACE_API_URL;

    /**
     * 제휴몰 유통 카테고리 조회
     * @return
     */
    public ResOutput selectCategory() {
        ResOutput resOutput = new ResOutput();
        ApiClient apiClient = new ApiClient();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String result = apiClient.marketPlace("GET", MARKETPLACE_API_URL + "/internal/category/channel-goods", null );
            List<Map<String, Object>> list = objectMapper.readValue(result, List.class);
            resOutput = getCategoryList(list);
            log.info("CategoryService.getCategoryList:" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resOutput;
    }

    public ResOutput getCategoryList(List<Map<String, Object>> list) {
        ResOutput response = new ResOutput();
        ArrayList<Category> categories = new ArrayList();

        for (Map<String, Object> item : list) {
            if (StringUtils.isNotEmpty(item.get("categoryId").toString())) {
                Category data = new Category();
                data.setName(item.get("categoryName").toString());
                data.setType(item.get("categoryId").toString());
                categories.add(data);
            }
        }
        response.setList(categories);

        return response;
    }
}
