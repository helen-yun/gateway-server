package com.pongift.naver.data.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


/**
 * 상품 목록 조회
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchProductsRes {
    /**
     * 콘텐츠 목록
     */
    private List<Contents> contents;
    /**
     * 페이지 번호
     */
    private int page;
    /**
     * 페이지 크기
     */
    private int size;
    /**
     * 전체 개수
     */
    private long totalElements;
    /**
     * 전체 페이지 수
     */
    private int totalPages;
    /**
     * 정렬 정보
     */
    private SortInfo sort;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;

    @Getter
    @Setter
    public static class Contents {
        /**
         * 원상품 번호
         */
        private long originProductNo;
        /**
         * 채널 상품 목록
         */
        private List<ChannelProduct> channelProducts;
    }

    @Getter
    @Setter
    public static class ChannelProduct {
        /**
         * 원상품 번호
         */
        private long originProductNo;
        /**
         * 채널 상품 번호
         */
        private long channelProductNo;
        /**
         * 채널 서비스 타입
         */
        private String channelServiceType;
        /**
         * 수급 상품 번호
         */
        private long supplyProductNo;
        /**
         * 카테고리 ID
         */
        private String categoryId;
        /**
         * 상품명
         */
        private String name;
        /**
         * 판매자 관리 코드
         */
        private String sellerManagementCode;
        /**
         * 상품 판매 상태 코드
         */
        private String statusType;
        /**
         * 채널 상품 전시 상태
         */
        private String channelProductDisplayStatusType;
        /**
         * 판매가
         */
        private long salePrice;
        /**
         * 할인가
         */
        private long discountedPrice;
        /**
         * 모바일 할인가
         */
        private long mobileDiscountedPrice;
        /**
         * 재고 수량
         */
        private int stockQuantity;
        /**
         * 판매 시작 일시
         */
        private String saleStartDate;
        /**
         * 판매 종료 일시
         */
        private String saleEndDate;
        /**
         * 상품 등록일
         */
        private String regDate;
        /**
         * 상품 수정
         */
        private String modifiedDate;
    }

    @Getter
    @Setter
    public static class SortInfo {
        /**
         * 데이터 정렬 적용 여부
         */
        private boolean sorted;
        /**
         * 정렬 적용 필드 정보
         */
        private List<SortField> fields;
        /**
         * 첫번째 페이지 여부
         */
        private boolean first;
        /**
         * 마지막 페이지 여부
         */
        private boolean last;


        @Getter
        @Setter
        public static class SortField {
            /**
             * 필드 이름
             */
            private String name;
            /**
             * 정렬 순서
             */
            private String direction;
        }
    }
}
