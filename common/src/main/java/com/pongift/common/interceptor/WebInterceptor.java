/*
 *  Copyright (c) 2019 GELIX, Inc.
 *  All right reserved.
 *  This software is the confidential and proprietary information of GELIX
 *  , Inc. You shall not disclose such Confidential Information and
 *  shall use it only in accordance with the terms of the license agreement
 *  you entered into with GELIX.
 *
 *  Revision History
 *  Author Date Description
 *  ------------------ -------------- ------------------
 *  dongpal.lee 2020. 3. 10.
 *
 */
package com.pongift.common.interceptor;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web 경로 접근 인터셉터
 * <p>
 * <pre>
 * com.pongift.interceptor
 * WebInterceptor.java
 * 개정이력(Modification Information)·
 * 수정일   수정자    수정내용
 * ------------------------------------
 * 2020. 3. 10.    dongpal.lee     최초작성
 * </pre>
 *  
 * @author dongpal.lee
 * @since 2020. 3. 10.
 * @version 1.0.0
 */
public class WebInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception exception) {
        logger.debug("WebInterceptor.afterCompletion()");
        
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
            ModelAndView exception) {
        logger.debug("WebInterceptor.postHandle()");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object exception) {

        // Context Path
        String contextPath = request.getContextPath();

        // Context Path를 제외한 URI
        String uri = request.getRequestURI().replaceAll(contextPath, "");

        // QueryString
        String queryString = request.getQueryString();
        
        // transactionId 발행
        String transactionId = UUID.randomUUID().toString();
        request.setAttribute("transactionId", transactionId);

        logger.debug("WebInterceptor.preHandle() contextPath = {} " , contextPath);
        logger.debug("WebInterceptor.preHandle() uri = {} " , uri);
        logger.debug("WebInterceptor.preHandle() queryString = {} " , queryString);
        logger.debug("WebInterceptor.preHandle() transactionId = {} " , transactionId);

//        logger.info("WebInterceptor.preHAndle() ------ {}", request.getHeader("origin"));

        return true;
    }
}
