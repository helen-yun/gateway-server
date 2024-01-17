package com.pongift.naver.service;

import com.pongift.common.data.model.domain.req.ReqGoodsParam;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.commerce.TokenService;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.CommerceCommonRes;
import com.pongift.naver.data.CommerceDataUtils;
import com.pongift.naver.data.model.req.CommerceProductsReq;
import com.pongift.naver.data.model.res.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final CommerceWebClient commerceWebClient;
    private final TokenService tokenService;

    /*==============================================커머스 API==============================================*/

    /**
     * 상품 상세 조회(네이버 커머스 API)
     *
     * @return
     */
    public ProductInfoRes getProductInfo(String goodsId) {
        ProductInfoRes productInfoRes = commerceWebClient.getProductInfo(tokenService.getAccessToken(), goodsId);
        log.info("productInfoRes : {}", JsonUtil.objectToStr(productInfoRes));

        return productInfoRes;
    }

    /**
     * 상품 목록 조회(네이버 커머스 API)
     *
     * @return
     */
    public SearchProductsRes getProductList() {
        return commerceWebClient.searchProductList(tokenService.getAccessToken());
    }

    /**
     * 상품 등록 (네이버 커머스 API)
     *
     * @param reqGoodsParam
     * @return RegProductsRes
     */
    public RegProductsRes regProduct(ReqGoodsParam reqGoodsParam) {
        CommerceProductsReq commerceProductsReq = CommerceDataUtils.createRegProductRequest(reqGoodsParam);
        log.info("commerceProductsReq : {}", JsonUtil.objectToStr(commerceProductsReq));

        return commerceWebClient.regProducts(tokenService.getAccessToken(), commerceProductsReq);
    }

    /**
     * 상품 수정 (네이버 커머스 API)
     *
     * @param reqGoodsParam
     * @return RegProductsRes
     */
    public RegProductsRes updateProduct(ReqGoodsParam reqGoodsParam) {
        CommerceProductsReq commerceProductsReq = CommerceDataUtils.createRegProductRequest(reqGoodsParam);
        log.info("commerceProductsReq : {}", JsonUtil.objectToStr(commerceProductsReq));

        return commerceWebClient.updateOriginProducts(tokenService.getAccessToken(), commerceProductsReq, reqGoodsParam.getGoodsId());
    }

    /**
     * 상품 삭제 (네이버 커머스 API)
     *
     * @param channelId
     * @return RegProductsRes
     */
    public CommerceCommonRes deleteProduct(String channelId) {
        return commerceWebClient.deleteOriginProducts(tokenService.getAccessToken(), channelId);
    }

    /**
     * 커머스 이미지 생성 (단건)
     *
     * @param image MultipartFile
     * @return url
     */
    public String uploadProductImage(MultipartFile image) {
        ImageUploadRes imageUploadRes = commerceWebClient.uploadProductImages(tokenService.getAccessToken(), image);
        return imageUploadRes.getImages().get(0).getUrl();
    }

    /**
     * 커머스 이미지 생성 (단건)
     *
     * @param url String
     * @return url
     */
    public String uploadProductImage(String url) {
        ImageUploadRes imageUploadRes = commerceWebClient.uploadProductImages(tokenService.getAccessToken(), url);
        return imageUploadRes.getImages().get(0).getUrl();
    }


    /**
     * 상품 판매 상태 변경
     * @param reqGoodsParam 판매 상태 변경 정보
     * @return
     */
    public RegProductsRes updateSaleStatus(ReqGoodsParam reqGoodsParam, CommerceProductsReq commerceProductsReq){
        RegProductsRes regProductsRes = commerceWebClient.updateOriginProducts(tokenService.getAccessToken(), commerceProductsReq, reqGoodsParam.getChannelId());
        log.info("regProductsRes : {}", JsonUtil.objectToStr(regProductsRes));

        return regProductsRes;
    }
}
