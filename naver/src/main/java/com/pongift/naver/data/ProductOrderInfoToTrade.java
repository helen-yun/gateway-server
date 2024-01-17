package com.pongift.naver.data;

import com.pongift.common.code.ChannelGb;
import com.pongift.common.code.PaymentGb;
import com.pongift.common.code.TradeTp;
import com.pongift.common.data.model.domain.dto.Buyer;
import com.pongift.common.data.model.domain.dto.Good;
import com.pongift.common.data.model.domain.dto.Receiver;
import com.pongift.common.data.model.domain.dto.Trade;
import com.pongift.naver.data.model.res.ChangedOrderInfoRes;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProductOrderInfoToTrade {

    /*==============================================제휴몰==================================================*/
    /**
     * 주문 상세(결제,취소)맵핑
     * @param param 주문 상세 데이터
     * @return
     */
    public Trade getTradeMapping(ChangedOrderInfoRes.OrderData param) {
        Trade trade = new Trade();
        trade.setChannelGb(ChannelGb.NAVER.getCode());  // 01	naver, 02	auction, 03	ebay, 04	wemakeprice, 05	ssg

        ChangedOrderInfoRes.Order order = param.getOrder();                  // 주문
        ChangedOrderInfoRes.ProductOrder productOrder = param.getProductOrder();   // 주문 상품

        trade.setTradeId(order.getOrderId());  // 거래ID
        trade.setTradeDate(CommerceDataUtils.fromOffsetDateFormat(order.getOrderDate()));

        if (productOrder != null && productOrder.getProductOrderStatus() != null) {
            log.info("## PAYED Status:" + productOrder.getProductOrderStatus());
            if (productOrder.getProductOrderStatus().equals("PAYED")) {
                trade.setTradeTp(TradeTp.SALES.getCode());  // 판매 상태(01:판매, 02:판매 승인, 03:판매 승인 취소 요청, 04:취소)
            }
            if (productOrder.getProductOrderStatus().equals("CANCELED")) {
                trade.setTradeTp(TradeTp.SALES_CANCEL.getCode());  // 판매 상태(01:판매, 02:판매 승인, 03:판매 승인 취소 요청, 04:취소)
            }
            if (productOrder.getProductOrderStatus().equals("RETURNED")) {
                trade.setTradeTp(TradeTp.SALES_CANCEL.getCode());  // 판매 상태(01:판매, 02:판매 승인, 03:판매 승인 취소 요청, 04:취소)
            }
        }

        ArrayList<Good> goodsList = new ArrayList<>();
        Good good = new Good();
        good.setProductOrderId(productOrder.getProductOrderId());
        good.setGoodsId(productOrder.getItemNo());
        good.setGoodsNm(productOrder.getProductName());
        good.setSaleCnt(productOrder.getQuantity());
        good.setSalePrice(productOrder.getTotalPaymentAmount());
        goodsList.add(good);

        Buyer buyer = new Buyer();
        log.info("order.getPaymentMeans() : {}", order.getPaymentMeans());
        String paymentMeans = order.getPaymentMeans();

        buyer.setPaymentGb(PaymentGb.findByName(paymentMeans).getCode());
        buyer.setName(order.getOrdererName());
        buyer.setPhoneNo(order.getOrdererTel().replaceAll("-", ""));

        trade.setGoodsList(goodsList);
        trade.setGoods(good);
        trade.setBuyer(buyer);

        Receiver receiver = new Receiver();
        if (productOrder.getShippingAddress() != null) {
            receiver.setName(productOrder.getShippingAddress().getName());
            receiver.setPhoneNo(productOrder.getShippingAddress().getTel1().replaceAll("-", ""));
            trade.setReceiver(receiver);
        }

        return trade;
    }
}
