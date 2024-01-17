package com.pongift.naver.data.model.res;

import lombok.*;

import java.util.List;


/**
 * 변경 상품 주문 상세 조회
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangedOrderInfoRes {
    private String traceId;
    private List<OrderData> data;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;

    @Getter
    @Setter
    public static class OrderData {
        private ProductOrder productOrder;
        private Delivery delivery;
        private Order order;
    }

    @Getter
    @Setter
    public static class ProductOrder {
        /**
         * 수량
         */
        private int quantity;
        /**
         * 상품 주문 번호
         */
        private String productOrderId;
        /**
         * 가맹점 ID
         */
        private String mallId;
        /**
         * 상품 종류(일반/추가 상품 구분)
         */
        private String productClass;
        /**
         * 상품 주문 상태
         */
        private String productOrderStatus;
        /**
         * 상품명
         */
        private String productName;
        /**
         * 채널 상품 번호
         */
        private String productId;
        /**
         * 원상품 번호
         */
        private String originalProductId;
        /**
         * 자동 생성 아이템 번호
         */
        private String itemNo;
        /**
         * 발주 상태
         */
        private String placeOrderStatus;
        /**
         * 상품 가격
         */
        private int unitPrice;
        /**
         * 옵션 금액
         */
        private int optionPrice;
        /**
         * 상품 별 할인액
         */
        private int productDiscountAmount;
        /**
         * 묶음 배송 번호
         */
        private String packageNumber;
        /**
         * 총 결제 금액
         */
        private int totalPaymentAmount;
        /**
         * 발송 기한
         */
        private String shippingDueDate;
        /**
         * 판매자 내부 사용 코드
         */
        private String sellerCustomCode1;
        /**
         * 옵션 코드
         */
        private String optionCode;
        /**
         * 발주 확인일
         */
        private String placeOrderDate;
        /**
         * 배송지 관련
         */
        private ShippingAddress shippingAddress;
        /**
         * 상품 주문 금액
         */
        private int totalProductAmount;
        /**
         * 판매자 부담 할인액
         */
        private int sellerBurdenDiscountAmount;
    }

    @Getter
    @Setter
    public static class Delivery {
        /**
         * 배송 방법 코드
         */
        private String deliveryMethod;
        /**
         * 발송 일시
         */
        private String sendDate;
        /**
         * 배송 상세 상태
         */
        private String deliveryStatus;
        /**
         * 오류 송장 여부(true=송장 오류)
         */
        private boolean isWrongTrackingNumber;
        /**
         * 배송 완료 일시
         */
        private String deliveredDate;
    }

    @Getter
    @Setter
    public static class Order {
        /**
         * 주문 번호
         */
        private String orderId;
        /**
         * 주문 일시
         */
        private String orderDate;
        /**
         * 결제 위치 구분
         */
        private String payLocationType;
        /**
         * 결제 일시(최종 결제)
         */
        private String paymentDate;
        /**
         * 주문 할인액
         */
        private int orderDiscountAmount;
        /**
         * 네이버 페이 적립금 최종 결제 금액
         */
        private int chargeAmountPaymentAmount;
        /**
         * 일반 결제 수단 최종 결제 금액
         */
        private int generalPaymentAmount;
        /**
         * 네이버페이 포인트 최종 결제 금액
         */
        private int naverMileagePaymentAmount;
        /**
         * 주문자 ID
         */
        private String ordererId;
        /**
         * 주문자 이름
         */
        private String ordererName;
        /**
         * 주문자 연락처
         */
        private String ordererTel;
        /**
         * 주문자 번호
         */
        private String ordererNo;
        /**
         * 결제 수단
         */
        private String paymentMeans;
        /**
         * 배송 메모 개별 입력 여부
         */
        private String isDeliveryMemoParticularInput;
        /**
         * 후불 결제 최종 결제 금액
         */
        private int payLaterPaymentAmount;
    }

    @Getter
    @Setter
    public static class ShippingAddress {
        /**
         * 배송지 타입
         */
        private String addressType;
        /**
         * 연락처
         */
        private String tel1;
        /**
         * 도로명 주소 여부
         */
        private boolean isRoadNameAddress;
        /**
         * 이름
         */
        private String name;
    }
}
