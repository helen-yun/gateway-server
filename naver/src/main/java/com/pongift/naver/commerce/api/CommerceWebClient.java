package com.pongift.naver.commerce.api;

import com.pongift.common.data.model.domain.req.TradeCancel;
import com.pongift.common.error.ApiException;
import com.pongift.naver.data.CommerceCommonRes;
import com.pongift.naver.data.CommerceDataUtils;
import com.pongift.naver.data.model.Time;
import com.pongift.naver.data.model.commerce.ByteArrayMultipartFile;
import com.pongift.naver.data.model.commerce.MultipartResource;
import com.pongift.naver.data.model.req.*;
import com.pongift.naver.data.model.res.ImageUploadRes;
import com.pongift.naver.data.model.res.*;
import com.pongift.naver.data.CommerceToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Slf4j
public class CommerceWebClient {
    private final WebClient webClient;

    public CommerceWebClient(String host) {
        //2중 encoding 방지를 위한 별도 builder 생성
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(host);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        this.webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(host).build();
    }


    /**
     * 인증 토큰 발급 요청 API
     *
     * @param clientId   제공된 애플리케이션 ID
     * @param secretSign 전자서명 생성 방법을 따라 생성된 전자서명
     * @param timestamp  전자서명 생성 시 사용된 밀리초(millisecond) 단위의 Unix 시간. 5분간 유효
     * @return CommerceToken
     */
    public CommerceToken token(String clientId, String secretSign, long timestamp) {
        log.info("### 커머스 토큰 발행!!");
        log.info("timestamp: {}", timestamp);
        String apiUrl = "/v1/oauth2/token";
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .queryParam("client_id", clientId)
                        .queryParam("timestamp", timestamp)
                        .queryParam("client_secret_sign", secretSign)
                        .queryParam("grant_type", "client_credentials")
                        .queryParam("type", "SELF")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("인증 토큰 발급 요청 ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("인증 토큰 발급 요청 ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(CommerceToken.class)
                .block();
    }

    /**
     * 전체 카테고리 조회
     *
     * @param token 인증 토큰
     * @return
     */
    public List<CategoryListRes> getCategoryList(String token) {
        String apiUrl = "/v1/categories";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("카테고리 조회 ERROR statusCode : {}", response.statusCode());
                            log.error("카테고리 조회 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(new ParameterizedTypeReference<List<CategoryListRes>>() {
                })
                .block();
    }

    /**
     * 카테고리 상세 조회
     *
     * @param token 인증 토큰
     * @return
     */
    public CategoryListRes getCategoryInfo(String token, String categoryId) {
        String apiUrl = "/v1/categories/" + categoryId;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("카테고리 상세 조회 ERROR statusCode : {}", response.statusCode());
                            log.error("카테고리 상세 조회 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(CategoryListRes.class)
                .block();
    }

    /**
     * 하위 카테고리 조회
     *
     * @param token 인증 토큰
     * @return
     */
    public List<CategoryListRes> getSubCategoryList(String token, String categoryId) {
        String apiUrl = "/v1/categories/" + categoryId + "/sub-categories";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("하위 카테고리 조회 ERROR statusCode : {}", response.statusCode());
                            log.error("하위 카테고리 조회 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(new ParameterizedTypeReference<List<CategoryListRes>>() {
                })
                .block();
    }

    /**
     * 상품 목록 조회
     *
     * @param token 인증 토큰
     * @return
     */
    public SearchProductsRes searchProductList(String token) {
        String apiUrl = "/v1/products/search";
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject("{}"))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("상품 목록 조회 ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("상품 목록 조회 ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(SearchProductsRes.class)
                .block();
    }

    /**
     * 상품 상세 조회
     *
     * @param token                      인증 토큰
     * @param smartstoreChannelProductNo 채널상품 번호
     * @return
     */
    public ProductInfoRes getProductInfo(String token, String smartstoreChannelProductNo) {
        String apiUrl = "/v2/products/channel-products/" + smartstoreChannelProductNo;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("상품 상세 조회 ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("상품 상세 조회 ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(ProductInfoRes.class)
                .block();
    }

    /**
     * (v2) 상품 등록
     *
     * @param token 인증 토큰
     * @return RegProductsRes
     */
    public RegProductsRes regProducts(String token, CommerceProductsReq param) {
        String apiUrl = "/v2/products";
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("상품 등록 ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("상품 등록 ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(RegProductsRes.class)
                .block();
    }

    /**
     * (v2) 상품 수정 (채널상품)
     *
     * @param token                      인증 토큰
     * @param smartstoreChannelProductNo 채널상품 번호
     * @return RegProductsRes
     */
    public RegProductsRes updateOriginProducts(String token, CommerceProductsReq param, String smartstoreChannelProductNo) {
        String apiUrl = "/v2/products/channel-products/{channelProductNo}";
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build(smartstoreChannelProductNo))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("상품 수정 (채널상품) ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("상품 수정 (채널상품) ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(RegProductsRes.class)
                .block();
    }

    /**
     * (v2) 상품 삭제 (채널상품)
     *
     * @param token                      인증 토큰
     * @param smartstoreChannelProductNo 채널상품 번호
     * @return RegProductsRes
     */
    public CommerceCommonRes deleteOriginProducts(String token, String smartstoreChannelProductNo) {
        String apiUrl = "/v2/products/channel-products/" + smartstoreChannelProductNo;
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.error("상품 삭제 (원상품) ERROR statusCode : {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .flatMap(errorResponseBody -> {
                                log.error("상품 삭제 (원상품) ERROR statusCode : {}", errorResponseBody);
                                return Mono.error(new ApiException(false, response.statusCode()));
                            });
                })
                .bodyToMono(CommerceCommonRes.class)
                .block();
    }

    /**
     * 상품 이미지 다건 등록
     *
     * @param token 인증 토큰
     * @param url   이미지 url
     * @return
     */
    public ImageUploadRes uploadProductImages(String token, String url) {
        String apiUrl = "/v1/product-images/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

        try {
            InputStream imageStream = new URL(url).openStream();
            byte[] imageData = CommerceDataUtils.readFromInputStream(imageStream);
            MultipartFile multipartFile = new ByteArrayMultipartFile(
                    imageData,
                    "imageField",
                    CommerceDataUtils.extractFileName(url),
                    "image/" + CommerceDataUtils.extractFileExtension(url));
            parts.add("imageFiles", new MultipartResource(multipartFile));
        } catch (IOException e) {
            throw new ApiException(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("상품 이미지 다건 등록 ERROR statusCode : {}", response.statusCode());
                            log.error("상품 이미지 다건 등록 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ImageUploadRes.class)
                .block();
    }

    /**
     * 상품 이미지 다건 등록
     *
     * @param token 인증 토큰
     * @return
     */
    public ImageUploadRes uploadProductImages(String token, MultipartFile image) {
        String apiUrl = "/v1/product-images/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);

        log.debug("image.getContentType : {}", image.getContentType());
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("imageFiles", new MultipartResource(image));

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("상품 이미지 다건 등록 ERROR statusCode : {}", response.statusCode());
                            log.error("상품 이미지 다건 등록 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ImageUploadRes.class)
                .block();
    }

    /**
     * 주문 목록 조회
     *
     * @param token 인증 토큰
     * @param type  조회 타입
     * @param time  조회 시간
     * @return
     */
    public ChangedOrdersRes getChangedOrderList(String token, String type, Time time) {
        String apiUrl = "/v1/pay-order/seller/product-orders/last-changed-statuses";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .queryParam("lastChangedFrom", time.getStartTime())
                        .queryParam("lastChangedTo", time.getEndTime())
                        .queryParam("lastChangedType", type)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("주문 목록 조회 ERROR statusCode : {}", response.statusCode());
                            log.error("주문 목록 조회 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ChangedOrdersRes.class)
                .block();
    }

    /**
     * 주문 상세 조회
     *
     * @param token 인증 토큰
     * @param param 상품 내역 정보
     * @return
     */
    public ChangedOrderInfoRes getChangedOrderInfo(String token, ChangedOrdersRes.LastChangeStatuses param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/query";
        List<String> list = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        list.add(param.getProductOrderId());
        map.put("productOrderIds", list);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(map))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("주문 상세 조회 ERROR statusCode : {}", response.statusCode());
                            log.error("주문 상세 조회 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ChangedOrderInfoRes.class)
                .block();
    }

    /**
     * 반품승인
     *
     * @param token 인등 토큰
     * @param param productOrderId 상품 주문 번호
     * @return
     */
    public ProductOrderRes returnApprove(String token, String param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/{productOrderId}/claim/return/approve";

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build(param))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("반품 승인 ERROR statusCode : {}", response.statusCode());
                            log.error("반품 승인 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }


    /**
     * 반품거부(철회)
     *
     * @param token 인증 토큰
     * @param rejectOrderReq 거부 요청값
     * @return
     */
    public ProductOrderRes rejectReturnRequest(String token, RejectOrderReq rejectOrderReq) {
        String apiUrl = "/v1/pay-order/seller/product-orders/{productOrderId}/claim/return/reject";

        Map<String, String> map = new HashMap<>();
        map.put("rejectReturnReason", rejectOrderReq.getRejectReturnReason());

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build(rejectOrderReq.getProductOrderId()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(map))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("반품 거부 ERROR statusCode : {}", response.statusCode());
                            log.error("반품 거부 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }


    /**
     * 취소 요청
     *
     * @param token 인증 토큰
     * @param param 취소 요청 값
     * @return
     */
    public ProductOrderRes cancelOrderRequest(String token, TradeCancel param, String reason) {
        String apiUrl = "/v1/pay-order/seller/product-orders/{productOrderId}/claim/cancel/request";

        Map<String, String> map = new HashMap<>();
        map.put("cancelReason", reason);

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build(param.getChannelOrderNo()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(map))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("취소 요청 ERROR statusCode : {}", response.statusCode());
                            log.error("취소 요청 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }

    /**
     * 발송처리
     *
     * @param token 인증 토큰
     * @param param 발송 요청 정보
     * @return
     */
    public ProductOrderRes shipProductOrder(String token, DispatchProductReq param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/dispatch";

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("발송 처리 ERROR statusCode : {}", response.statusCode());
                            log.error("발송 처리 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }

    /**
     * E-Coupon 사용처리
     *
     * @param token 인증 토큰
     * @param param E-쿠폰 정보
     * @return
     */
    public ProductOrderRes changeECouponStatus(String token, ECouponReq param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/e-coupon/status/change";

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("E-Coupon 사용 처리 ERROR statusCode : {}", response.statusCode());
                            log.error("E-Coupon 사용 처리 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }

    /**
     * E-Coupon 발송처리
     *
     * @param token 인증 토큰
     * @param param E-쿠폰 정보
     * @return
     */
    public ProductOrderRes eCouponDispatch(String token, ECouponReq param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/e-coupon/dispatch";

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("E-Coupon 발송 처리 ERROR statusCode : {}", response.statusCode());
                            log.error("E-Coupon 발송 처리 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }

    /**
     * e-쿠폰 유효기간 연장
     *
     * @param token
     * @param param
     * @return
     */
    public ProductOrderRes eCouponValidDateChange(String token, ECouponReq param) {
        String apiUrl = "/v1/pay-order/seller/product-orders/e-coupon/valid-date/change";

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(param))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("E-Coupon 유효기간 연장 ERROR statusCode : {}", response.statusCode());
                            log.error("E-Coupon 유효기간 연장 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ProductOrderRes.class)
                .block();
    }

    /**
     * 판매 상태 변경
     *
     * @param token           인증 토큰
     * @param changeStatusReq 판매 상태 변경 정보
     * @return
     */
    public ChangeStatusRes updateSaleStatus(String token, ChangeStatusReq changeStatusReq) {
        String apiUrl = "/v1/products/origin-products/{originProductNo}/change-status";

        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl)
                        .build(changeStatusReq.getOriginProductNo()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromObject(changeStatusReq))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(responseBody -> {
                            log.error("판매 상태 변경 ERROR statusCode : {}", response.statusCode());
                            log.error("판매 상태 변경 ERROR body : {}", responseBody);
                            return Mono.error(new ApiException(false, response.statusCode()));
                        }))
                .bodyToMono(ChangeStatusRes.class)
                .block();
    }
}
