package com.pongift.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pongift.common.data.model.domain.res.ApiOutput;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dongpal.lee on 20/03/2020.
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String objectToStr(Object object) {
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.info("Exception : " + e.getMessage());
            e.printStackTrace();
        } finally {
            return jsonStr;
        }

    }

    public static Object strToObject(String json) throws Exception {
        Object object = mapper.readValue(json, Object.class);
        return object;
    }

    public static ApiOutput getApiOutput(String json) throws Exception {
        ApiOutput apiOutput = mapper.readValue(json, new TypeReference<ApiOutput>() {});
        return apiOutput;
    }
}
