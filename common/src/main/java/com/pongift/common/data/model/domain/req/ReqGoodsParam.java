package com.pongift.common.data.model.domain.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReqGoodsParam extends CommonRequest {
    /**
     * <p>채널 상품 ID</p>
     */
    private String channelId;
    /**
     * <p>상품 ID</p>
     */
    private String goodsId;
    /**
     * <p>카테고리</p>
     */
    private ArrayList<String> categories;
    /**
     * <p>상품명</p>
     */
    private String goodsNm;
    /**
     * <p>재고수량</p>
     */
    private int stockCnt;
    /**
     * <p>실판매가</p>
     */
    private int salePrice;
    /**
     * <p>소비자 가격(정상가)</p>
     */
    private int retailPrice;
    /**
     * <p>할인가</p>
     */
    private int discountPrice;
    /**
     * <p>상품 전시 상태</p>
     * <ul>
     *     <li>01 : 전시대기</li>
     *     <li>02 : 전시판매</li>
     *     <li>03 : 전시중지</li>
     *     <li>04 : 전시품절</li>
     * </ul>
     */
    private String exhibitSt;
    /**
     * <p>유효기간</p>
     */
    private String expiryGb;
    /**
     * <p>상품 정보</p>
     */
    private String html;
    /**
     * <p>대표이미지</p>
     */
    private String goodsImage;
    /**
     * <p>판매 일시(YYYYMMDD24HHMISS)</p>
     */
    private String regDate;
    /**
     * <p>변경일시</p>
     */
    private String modDate;
    /**
     * <p>상품 카테고리 코드 (검색용, 옵션)</p>
     */
    private String categoryTp;
    /**
     * <p>승인상태</p>
     * <ul>
     *     <li>01 : 미승인</li>
     *     <li>02 : 승인</li>
     *     <li>03 : 반려</li>
     * </ul>
     */
    private String approvalSt;
    /**
     * <p>상품명 키워드(검색용, 옵션)</p>
     */
    private String keywordGoodsNm;
    /**
     * <p>정렬순서 코드</p>
     * <ul>
     *     <li>01 : 등록일</li>
     *     <li>02 : 최신 (기본 값)</li>
     *     <li>03 : 상품명</li>
     *     <li>04 : 낮은가격</li>
     *     <li>05 : 높은가격</li>
     * </ul>
     */
    private String orderCd;
    /**
     * <p>재고 수정 번호</p>
     */
    private Long stockNo;
    private int productGroupNoticeNo;
    /**
     * <p>거래수량</p>
     */
    private int saleCnt;
    /**
     * <p>판매기간 시작일 (yyyy-MM-dd HH:00) ※ 판매시작 이후 변경 불가<br>(상품 업데이트 위한 필수값)</p>
     */
    private String saleStartDate;
    /**
     * <p>판매기간 종료일 (yyyy-MM-dd HH:00)<br>(상품 업데이트 위한 필수값)</p>
     */
    private String saleEndDate;
    /**
     * <p>판매자 상품 ID<br>(상품 업데이트 위한 필수값)</p>
     */
    private int sellerProdCode;
    /**
     * <p>상품 고시정보</p>
     */
    private String itemMngPropClsId;

    /**
     * 제휴몰 관련 parameter 통일을 위해 추가
     */
    private String infoUrl;
    private String storeUrl;
    private String storeName;
    private String storeId;
    private long storeSeq;
}
