package com.pongift.naver.data.model.commerce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OriginProduct {
    /**
     * 상품 ID
     */
    @Setter
    private String goodsId;
    /**
     * 상품 판매 상태 코드
     */
    private StatusType statusType;
    /**
     * 상품 판매 유형 코드
     */
    private SaleType saleType;
    /**
     * 리프 카테고리 ID.
     * 상품 등록 시에는 필수입니다. 표준형 옵션 카테고리 상품 수정 요청의 경우 CategoryId 변경 요청은 무시됩니다.
     */
    private String leafCategoryId;
    /**
     * 상품명
     */
    private String name;
    /**
     * 상품 상세 정보.
     * 상품 수정 시에만 생략할 수 있습니다. 이 경우 기존에 저장된 상품 상세 정보 값이 유지됩니다.
     */
    private String detailContent;
    /**
     * 상품 이미지로 대표 이미지(1000x1000픽셀 권장)와 최대 9개의 추가 이미지 목록을 제공할 수 있습니다.
     * 대표 이미지는 필수이고 추가 이미지는 선택 사항입니다.
     * 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
     */
    private Images images;
    /**
     * 판매 시작 일시
     */
    private String saleStartDate;
    /**
     * 판매 종료 일시
     */
    private String saleEndDate;
    /**
     * 상품 판매 가격 (10원 단위)
     */
    private long salePrice;
    /**
     * 재고 수량.
     * 상품 등록 시 필수. 상품 수정 시 재고 수량을 입력하지 않으면 스마트스토어 데이터베이스에 저장된 현재 재고 값이 변하지 않습니다.
     * 수정 시 재고 수량이 0으로 입력되면 StatusType으로 전달된 항목은 무시되며 상품 상태는 OUTOFSTOCK(품절)으로 저장됩니다.
     */
    private int stockQuantity;
    /*
    private DeliveryInfo deliveryInfo;
    private List<ProductLogistics> productLogistics;
    */
    private DetailAttribute detailAttribute;

    private CustomerBenefit customerBenefit;


    /**
     * 상품 판매 상태 코드
     * WAIT(판매 대기), SALE(판매 중), OUTOFSTOCK(품절), UNADMISSION(승인 대기), REJECTION(승인 거부), SUSPENSION(판매 중지), CLOSE(판매 종료), PROHIBITION(판매 금지)
     */
    public enum StatusType {
        WAIT, SALE, OUTOFSTOCK, UNADMISSION, REJECTION, SUSPENSION, CLOSE, PROHIBITION, DELETE
    }

    /**
     * 상품 판매 유형 코드.
     * 상품 API에서 상품의 판매 유형을 나타내기 위해 사용하는 코드입니다. 미입력 시 NEW(새 상품)로 저장됩니다.
     */
    public enum SaleType {
        NEW, OLD
    }

    /**
     * 추가 이미지 목록. 최대 9개. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Images {
        private RepresentativeImage representativeImage;
        private List<RepresentativeImage> optionalImages;

        @Data
        public static class RepresentativeImage {
            /**
             * 이미지 URL
             */
            private String url;
        }
    }

    @Data
    public static class DetailAttribute {
        /**
         * A/S 정보
         */
        private AfterServiceInfo afterServiceInfo;
        /**
         * 원산지 정보
         */
        private OriginAreaInfo originAreaInfo;
        /**
         * 구매 수량 정보
         */
        private PurchaseQuantityInfo purchaseQuantityInfo;
        /**
         * 상품 정보 제공 고시
         */
        private ProductInfoProvidedNotice productInfoProvidedNotice;
        /**
         * E 쿠폰
         */
        private Ecoupon ecoupon;
        /**
         * 미성년자 구매 가능 여부
         */
        private boolean minorPurchasable;

        /**
         * A/S 정보
         */
        @Data
        public static class AfterServiceInfo {
            /**
             * A/S 전화번호
             */
            private String afterServiceTelephoneNumber;
            /**
             * A/S 안내
             */
            private String afterServiceGuideContent;
        }

        /**
         * 원산지 정보
         */
        @Data
        public static class OriginAreaInfo {
            /**
             * 원산지 상세 지역 코드
             */
            private String originAreaCode;
        }

        /**
         * 구매 수량 정보
         */
        @Data
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        public static class PurchaseQuantityInfo {
            /**
             * 최소 구매 수량
             */
            private int minPurchaseQuantity;
            /**
             * 1인 최대 구매 수량
             */
            private int maxPurchaseQuantityPerId;
            /**
             * 1회 최대 구매 수량
             */
            private int maxPurchaseQuantityPerOrder;
        }

        /**
         * 상품정보제공고시
         */
        @Data
        public static class ProductInfoProvidedNotice {
            private MobileCoupon mobileCoupon;
            private ProductInfoProvidedNoticeType productInfoProvidedNoticeType;
            /**
             * 모바일 쿠폰 상품정보제공고시
             */
            @Data
            public static class MobileCoupon {
                /**
                 * 제품하자/오배송에 따른 청약철회 조항 / 1(상품상세 참조)
                 */
                private String returnCostReason;
                /**
                 * 제품하자가 아닌 소비자의 단순변심에 따른 청약철회가 불가능한 경우 그 구체적 사유와 근거 / 1(상품상세 참조)
                 */
                private String noRefundReason;
                /**
                 * 재화 등의 교환ㆍ반품ㆍ보증 조건 및 품질 보증 기준 / 1(상품상세 참조)
                 */
                private String qualityAssuranceStandard;
                /**
                 * 대금을 환불받기 위한 방법과 환불이 지연될 경우 지연배상금을 지급받을 수 있다는 사실 및 배상금 지급의 구체적인 조건·절차 / 1(상품상세 참조)
                 */
                private String compensationProcedure;
                /**
                 * 소비자피해보상의 처리, 재화 등에 대한 불만 처리 및 소비자와 사업자 사이의 분쟁 처리에 관한 사항 / 1(상품상세 참조)
                 */
                private String troubleShootingContents;
                /**
                 * 발행자
                 */
                private String issuer;
                /**
                 * 유효기간, 이용 조건
                 */
                private String usableCondition;
                /**
                 * 이용 가능 매장
                 */
                private String usableStore;
                /**
                 * 환불 조건 및 방법
                 */
                private String cancelationPolicy;
                /**
                 * 소비자 상담 관련 전화번호
                 */
                private String customerServicePhoneNumber;
            }
            /**
             * 상품정보제공고시 타입 (모바일 쿠폰 이외 커머스 API 참조..)
             */
            public enum ProductInfoProvidedNoticeType {
                MOBILE_COUPON
            }
        }

        /**
         * E 쿠폰
         */
        @Data
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Ecoupon {
            /**
             * E쿠폰 유효기간 구분 코드
             */
            private PeriodType periodType;
            /**
             * E쿠폰 유효기간 내용(구매일로부터 00일)
             */
            private int periodDays;
            /**
             * E쿠폰 발행처 내용
             */
            private String publicInformationContents;
            /**
             * E쿠폰 연락처 내용
             */
            private String contactInformationContents;
            /**
             * E쿠폰 사용 장소 구분 코드
             */
            private UsePlaceType usePlaceType;
            /**
             * 사용 장소 내용
             */
            private String usePlaceContents;
            /**
             * 장바구니 구매 불가 여부 true: 즉시 구매만 가능, false: 즉시 구매, 장바구니 구매 가능
             */
            private boolean restrictCart;

            /**
             * E쿠폰 유효기간 구분 코드 FIXED(특정 기간), FLEXIBLE(자동 기간)
             */
            public enum PeriodType {
                FIXED, FLEXIBLE
            }

            /**
             * E쿠폰 사용 장소 구분 코드 PLACE(장소), ADDRESS(주소), URL(URL)
             */
            public enum UsePlaceType {
                PLACE, ADDRESS, URL
            }
        }
    }
    /**
     * 상품 고객 혜택 정보
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class CustomerBenefit {
        private ImmediateDiscountPolicy immediateDiscountPolicy;
        /**
         * 판매자 즉시 할인 정책
         */
        @Data
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class ImmediateDiscountPolicy {
            private DiscountMethod discountMethod;
            private MobileDiscountMethod mobileDiscountMethod;
            
            /**
             * PC 할인 혜택
             */
            @Data
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            public static class DiscountMethod {
                /**
                 * 할인 값
                 * 정율 10%이면 10, 정액 100원이면 100
                 */
                private int value;
                /**
                 * 할인 단위 타입. PERCENT, WON만 입력 가능합니다.
                 */
                private UnitType unitType;

                public enum UnitType {
                    PERCENT, WON, YEN, COUNT
                }
            }

            /**
             * 모바일 할인 혜택
             */
            @Data
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            public static class MobileDiscountMethod {
                /**
                 * 할인 값
                 * 정율 10%이면 10, 정액 100원이면 100
                 */
                private int value;
                /**
                 * 할인 단위 타입. PERCENT, WON만 입력 가능합니다.
                 */
                private UnitType unitType;

                public enum UnitType {
                    PERCENT, WON, YEN, COUNT
                }
            }
        }
    }
}
