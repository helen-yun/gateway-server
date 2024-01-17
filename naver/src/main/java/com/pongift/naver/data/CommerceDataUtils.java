package com.pongift.naver.data;

import com.pongift.common.code.ExhibitSt;
import com.pongift.common.code.ExpiryGb;
import com.pongift.common.data.model.domain.dto.Category;
import com.pongift.common.data.model.domain.dto.Good;
import com.pongift.common.data.model.domain.req.ReqGoodsParam;
import com.pongift.naver.data.model.commerce.OriginProduct;
import com.pongift.naver.data.model.commerce.SmartstoreChannelProduct;
import com.pongift.naver.data.model.req.CommerceProductsReq;
import com.pongift.naver.data.model.res.SearchProductsRes;
import com.pongift.naver.data.model.res.ProductInfoRes;
import com.pongift.naver.data.model.Time;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLEncoder;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommerceDataUtils {
    /**
     * CustomDateTime(iso 8601 형식, utf-8 인코딩)
     * 현재 시간 기준 12시 이전이면 시작 시간 00:00 아니면 12:00
     */
    public static Time customIsoEncodeTime() {
        Time time = new Time();

        OffsetDateTime startTime;
        OffsetDateTime endTime;

        // 현재 시간 정보 가져오기
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset offset = ZoneOffset.ofHours(9); //+09:00 오프셋을 사용

        // 날짜의 낮 12시와 낮 12시 이후의 시간을 계산
        LocalDateTime noon = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);

        // startTime 설정
        if (now.isBefore(noon)) {
            startTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.NOON).atOffset(offset);
        } else {
            startTime = LocalDateTime.of(LocalDate.now(), LocalTime.NOON).atOffset(offset);
        }

        //endTime(+24hours)
        endTime = startTime.plusHours(24);

        String isoStartTime = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        String isoEndTime = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        /**
         * 시작 시간 설정
         */
        String startInput = isoStartTime;
        String encodedStartTime = null;
        try {
            encodedStartTime = URLEncoder.encode("+", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String startEncodedInput = startInput.replace("+", encodedStartTime);

        /**
         * 종료 시간 설정
         */
        String endInput = isoEndTime;
        String encodedEndTime = null;
        try {
            encodedEndTime = URLEncoder.encode("+", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String EndEncodedInput = endInput.replace("+", encodedEndTime);

        time.setStartTime(startEncodedInput);
        time.setEndTime(EndEncodedInput);

        return time;
    }

    /**
     * iso 8601 형식 현재 dateTime 가져오기
     *
     * @return
     */
    public static String customIsoTime() {
        OffsetDateTime offsetDateTime;
        String isoDateTime;

        // 현재 시간 정보 가져오기
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset offset = ZoneOffset.ofHours(9);

        offsetDateTime = now.atOffset(offset);
        isoDateTime = offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        return isoDateTime;
    }

    /**
     * offsetDate 포맷 변환
     *
     * @param date
     * @return
     */
    public static String fromOffsetDateFormat(String date) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formattedDate = offsetDateTime.format(outputFormatter);

        return formattedDate;
    }

    /**
     * offsetDateFormat 변환
     * (yyyyMMdd'T'HH:mm:ss.SSSXXX 형식)
     *
     * @param date
     * @return
     */
    public static String toOffsetDateFormat(String date) {
        String inputDate = date;
        String inputFormat = "yyyyMMdd'T'HH:mm:ss.SSSXXX";
        String outputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

        // Parse input string using the input format
        OffsetDateTime dateTime = OffsetDateTime.parse(inputDate, DateTimeFormatter.ofPattern(inputFormat));

        // Format the parsed datetime using the desired output format
        String output = dateTime.format(DateTimeFormatter.ofPattern(outputFormat));

        return output;
    }

    /**
     * 상품 등록 API request 값 매핑
     *
     * @param reqGoodsParam
     * @return
     */
    public static CommerceProductsReq createRegProductRequest(ReqGoodsParam reqGoodsParam) {
        /**
         * 대표 이미지
         */
        OriginProduct.Images.RepresentativeImage representativeImage = new OriginProduct.Images.RepresentativeImage();
        representativeImage.setUrl(reqGoodsParam.getGoodsImage());
        OriginProduct.Images images = new OriginProduct.Images();
        images.setRepresentativeImage(representativeImage);

        /**
         * 원상품 속성 세팅
         */
        // A/S 정보
        OriginProduct.DetailAttribute.AfterServiceInfo afterServiceInfo = new OriginProduct.DetailAttribute.AfterServiceInfo();
        afterServiceInfo.setAfterServiceTelephoneNumber("02-597-0660");
        afterServiceInfo.setAfterServiceGuideContent("상품 상세 참조");
        // 구매 수량 정보
        OriginProduct.DetailAttribute.PurchaseQuantityInfo purchaseQuantityInfo = new OriginProduct.DetailAttribute.PurchaseQuantityInfo();
        purchaseQuantityInfo.setMaxPurchaseQuantityPerId(9999);
        purchaseQuantityInfo.setMaxPurchaseQuantityPerOrder(1);
        // 원산지 정보
        OriginProduct.DetailAttribute.OriginAreaInfo originAreaInfo = new OriginProduct.DetailAttribute.OriginAreaInfo();
        originAreaInfo.setOriginAreaCode("00");
        // E 쿠폰
        OriginProduct.DetailAttribute.Ecoupon ecoupon = new OriginProduct.DetailAttribute.Ecoupon();
        ecoupon.setPeriodType(OriginProduct.DetailAttribute.Ecoupon.PeriodType.FLEXIBLE);
        ecoupon.setPeriodDays(ExpiryGb.findValueByCode(reqGoodsParam.getExpiryGb()));
        ecoupon.setPublicInformationContents("(주)플랫포스");
        ecoupon.setContactInformationContents("02-597-0660");
        ecoupon.setUsePlaceType(OriginProduct.DetailAttribute.Ecoupon.UsePlaceType.PLACE);
        ecoupon.setUsePlaceContents("상품상세 참조");
        ecoupon.setRestrictCart(false);
        // 상품 정보 제공 고시
        OriginProduct.DetailAttribute.ProductInfoProvidedNotice productInfoProvidedNotice = new OriginProduct.DetailAttribute.ProductInfoProvidedNotice();
        OriginProduct.DetailAttribute.ProductInfoProvidedNotice.MobileCoupon mobileCoupon = new OriginProduct.DetailAttribute.ProductInfoProvidedNotice.MobileCoupon();
        mobileCoupon.setReturnCostReason("상품상세 참조");
        mobileCoupon.setNoRefundReason("상품상세 참조");
        mobileCoupon.setQualityAssuranceStandard("상품상세 참조");
        mobileCoupon.setCompensationProcedure("상품상세 참조");
        mobileCoupon.setTroubleShootingContents("상품상세 참조");
        mobileCoupon.setIssuer("(주)플랫포스");
        mobileCoupon.setUsableCondition("상품상세 참조");
        mobileCoupon.setUsableStore("상품상세 참조");
        mobileCoupon.setCancelationPolicy("상품상세 참조");
        mobileCoupon.setCustomerServicePhoneNumber("02-597-0660");
        productInfoProvidedNotice.setProductInfoProvidedNoticeType(OriginProduct.DetailAttribute.ProductInfoProvidedNotice.ProductInfoProvidedNoticeType.MOBILE_COUPON);
        productInfoProvidedNotice.setMobileCoupon(mobileCoupon);

        // 원상품 속성
        OriginProduct.DetailAttribute detailAttribute = new OriginProduct.DetailAttribute();
        detailAttribute.setAfterServiceInfo(afterServiceInfo);
        detailAttribute.setPurchaseQuantityInfo(purchaseQuantityInfo);
        detailAttribute.setOriginAreaInfo(originAreaInfo);
        detailAttribute.setEcoupon(ecoupon);
        detailAttribute.setProductInfoProvidedNotice(productInfoProvidedNotice);
        detailAttribute.setMinorPurchasable(true);

        OriginProduct.CustomerBenefit customerBenefit = new OriginProduct.CustomerBenefit();
        OriginProduct.CustomerBenefit.ImmediateDiscountPolicy immediateDiscountPolicy = new OriginProduct.CustomerBenefit.ImmediateDiscountPolicy();
        OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.DiscountMethod discountMethod = new OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.DiscountMethod();
        discountMethod.setValue(reqGoodsParam.getDiscountPrice());
        discountMethod.setUnitType(OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.DiscountMethod.UnitType.WON);
        OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.MobileDiscountMethod mobileDiscountMethod = new OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.MobileDiscountMethod();
        mobileDiscountMethod.setValue(reqGoodsParam.getDiscountPrice());
        mobileDiscountMethod.setUnitType(OriginProduct.CustomerBenefit.ImmediateDiscountPolicy.MobileDiscountMethod.UnitType.WON);
        immediateDiscountPolicy.setDiscountMethod(discountMethod);
        immediateDiscountPolicy.setMobileDiscountMethod(mobileDiscountMethod);
        //할인가 있을 경우만 셋팅
        if(reqGoodsParam.getDiscountPrice() > 0){
            customerBenefit.setImmediateDiscountPolicy(immediateDiscountPolicy);
        }

        /**
         * 원상품 세팅
         */
        OriginProduct originProduct = new OriginProduct();
        originProduct.setStatusType(ExhibitSt.STOP.getCode().equals(reqGoodsParam.getExhibitSt()) ? OriginProduct.StatusType.SUSPENSION : OriginProduct.StatusType.SALE);
        originProduct.setName(reqGoodsParam.getGoodsNm());
        originProduct.setLeafCategoryId(reqGoodsParam.getCategories().get(0));
        originProduct.setDetailContent(reqGoodsParam.getHtml());
        originProduct.setImages(images);
        originProduct.setStockQuantity(reqGoodsParam.getStockCnt());
        originProduct.setDetailAttribute(detailAttribute);
        originProduct.setCustomerBenefit(customerBenefit);

        /**
         * 판매가 세팅
         */
        if (reqGoodsParam.getSalePrice() > reqGoodsParam.getRetailPrice()) {
            originProduct.setSalePrice(reqGoodsParam.getSalePrice());
        } else {
            originProduct.setSalePrice(reqGoodsParam.getRetailPrice());
        }
        
        /**
         * 채널 상품 세팅
         */
        SmartstoreChannelProduct smartstoreChannelProduct = new SmartstoreChannelProduct();
        smartstoreChannelProduct.setNaverShoppingRegistration(false);

        if (ExhibitSt.SALE.getCode().equals(reqGoodsParam.getExhibitSt())) { // 전시
            smartstoreChannelProduct.setChannelProductDisplayStatusType(SmartstoreChannelProduct.ChannelProductDisplayStatusType.ON);
        } else {
            smartstoreChannelProduct.setChannelProductDisplayStatusType(SmartstoreChannelProduct.ChannelProductDisplayStatusType.SUSPENSION);
        }

        return new CommerceProductsReq(originProduct, smartstoreChannelProduct);
    }

    /**
     * 상품 조회 후 업데이트 맵핑(판매 상태 변경)
     * @param productInfoRes 채널 상품 상세
     * @param reqGoodsParam api->gateway 상품 상세
     * @return
     */
    public static CommerceProductsReq updateRegProductRequest(ProductInfoRes productInfoRes, ReqGoodsParam reqGoodsParam){
        //변경 상태값 셋팅
        if ((ExhibitSt.SALE.getCode()).equals(reqGoodsParam.getExhibitSt())) {
            productInfoRes.getOriginProduct().setStatusType(OriginProduct.StatusType.SALE);  // SALE(판매 중)
        } else if (ExhibitSt.OSTK.getCode().equals(reqGoodsParam.getExhibitSt())) {
            productInfoRes.getOriginProduct().setStatusType(OriginProduct.StatusType.OUTOFSTOCK);  // OUTOFSTOCK(품절)
        } else {
            productInfoRes.getOriginProduct().setStatusType(OriginProduct.StatusType.SUSPENSION);  // SUSPENSION(판매 중지),
        }
        /*productInfoRes.getOriginProduct().setStockQuantity(100);*/
        return new CommerceProductsReq(productInfoRes.getOriginProduct(), productInfoRes.getSmartstoreChannelProduct());
    }

    /**
     * inputStrem 바이너리로 읽어오기
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

    /**
     * url 확장자 가져오기
     * @param fileName
     * @return
     */
    public static String extractFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        } else {
            return "";
        }
    }

    /**
     * url 파일 이름 가져오기
     * @param urlString
     * @return
     */
    public static String extractFileName(String urlString) {
        int lastSlashIndex = urlString.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < urlString.length() - 1) {
            return urlString.substring(lastSlashIndex + 1);
        } else {
            return "";
        }
    }
}