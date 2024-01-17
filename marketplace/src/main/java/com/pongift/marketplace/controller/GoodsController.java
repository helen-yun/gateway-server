package com.pongift.marketplace.controller;

import com.pongift.common.data.model.domain.dto.Good;
import com.pongift.common.data.model.domain.dto.Page;
import com.pongift.common.data.model.domain.req.ReqGoodsParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.error.ApiException;
import com.pongift.common.utils.JsonUtil;
import com.pongift.marketplace.data.req.GoodsReq;
import com.pongift.marketplace.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;


    /**
     * 상품 조회
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "상품 조회")
    @GetMapping("")
    public ResponseEntity<ResOutput> selectGoods(@RequestParam(value = "goodsId") String goodsId) {
        log.info("==================================================");
        log.info("제휴몰 상품조회");
        log.info("Param : {}", JsonUtil.objectToStr(goodsId));
        log.info("//================================================");

        ResOutput resOutput = goodsService.selectGoods(goodsId);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    /**
     * 상품 등록
     * @param goodsReq
     * @return
     */
    @ApiOperation(value = "상품 등록")
    @PostMapping("")
    public ResponseEntity<ResOutput> createGoods(@RequestBody GoodsReq goodsReq) {
        ResOutput resOutput = new ResOutput();
        log.info("==================================================");
        log.info("제휴몰 상품등록");
        log.info("Param : {}", JsonUtil.objectToStr(goodsReq));
        log.info("//================================================");
        if (goodsReq.getStoreSeq() <= 0) {
            resOutput.setResponse(new Response(false, "storeSeq is required"));
            return new ResponseEntity<>(resOutput, HttpStatus.BAD_REQUEST);
        }else if(!StringUtils.hasText(goodsReq.getStoreName())){
            resOutput.setResponse(new Response(false, "storeName is required"));
            return new ResponseEntity<>(resOutput, HttpStatus.BAD_REQUEST);
        }
        resOutput = goodsService.insertGoods(goodsReq);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    /**
     * 상품 수정
     * @param goodsReq
     * @return
     */
    @ApiOperation(value = "상품 수정")
    @PutMapping("")
    public ResponseEntity<ResOutput> updateGoods(@RequestBody GoodsReq goodsReq) {
        ResOutput resOutput = new ResOutput();
        log.info("==================================================");
        log.info("제휴몰 상품수정");
        log.info("Param : {}", JsonUtil.objectToStr(goodsReq));
        log.info("//================================================");

        if (goodsReq.getStoreSeq() <= 0) {
            resOutput.setResponse(new Response(false, "storeSeq is required"));
            return new ResponseEntity<>(resOutput, HttpStatus.BAD_REQUEST);
        }

        resOutput = goodsService.updateGoods(goodsReq);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 판매 상태 변경(제휴몰)")
    @PostMapping("/exhibitSt")
    public ResponseEntity<ResOutput> updateSaleStatus(@RequestBody ReqGoodsParam param) {
        ResOutput resOutput = new ResOutput();
        log.info("==================================================");
        log.info("제휴몰 판매 상태 변경");
        log.info("Param : {}", JsonUtil.objectToStr(param));
        log.info("//================================================");

        //전시 중지 처리
        if(param.getExhibitSt().equals("04")){
            resOutput = goodsService.updateSaleStatus(param.getChannelId());
        }

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
