package com.pongift.naver.controller;

import com.pongift.common.data.model.domain.dto.Good;
import com.pongift.common.data.model.domain.dto.Page;
import com.pongift.common.data.model.domain.req.ReqGoodsParam;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.utils.JsonUtil;
import com.pongift.naver.data.CommerceCommonRes;
import com.pongift.naver.data.CommerceDataUtils;
import com.pongift.naver.data.model.res.ProductInfoRes;
import com.pongift.naver.data.model.res.RegProductsRes;
import com.pongift.naver.data.model.res.SearchProductsRes;
import com.pongift.naver.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
public class ProductController {
    private final ProductService productService;

    /*==============================================커머스 API==============================================*/
    @ApiOperation(value = "상품 목록(커머스 API)")
    @GetMapping("")
    public ResponseEntity<ResOutput> getProductList(@ApiIgnore ReqGoodsParam param) {
        log.debug("==================================================");
        log.debug("네이버 커머스-상품목록");
        log.debug("//================================================");
        ResOutput resOutput = new ResOutput();
        // 2.0 기준에 맞춰서..
        if(param.getGoodsId() != null) {
            ProductInfoRes productInfoRes = productService.getProductInfo(param.getGoodsId());
            resOutput.setData(productInfoRes);
        } else {
            SearchProductsRes searchProductsRes = productService.getProductList();
            resOutput.setData(searchProductsRes);
        }

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 상세(커머스 API)")
    @GetMapping("/{goodsId}")
    public ResponseEntity<ResOutput> getProductDetail(@PathVariable("goodsId") String goodsId) {
        log.debug("==================================================");
        log.debug("네이버 커머스-상품상세");
        log.debug("goodsId : {}", goodsId);
        log.debug("//================================================");
        ProductInfoRes productInfoRes = productService.getProductInfo(goodsId);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(productInfoRes);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 등록(커머스 API)")
    @PostMapping("")
    public ResponseEntity<ResOutput> saveProduct(@RequestBody ReqGoodsParam param) {
        log.debug("==================================================");
        log.debug("스마트스토어 상품등록");
        log.debug("Param : {}", JsonUtil.objectToStr(param));
        log.debug("//================================================");

        String goodsImageUrl = productService.uploadProductImage(param.getGoodsImage());
        param.setGoodsImage(goodsImageUrl);
        log.debug("goodsImageUrl = {}", param.getGoodsImage());

        RegProductsRes regProductsRes = productService.regProduct(param);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(String.valueOf(regProductsRes.getSmartstoreChannelProductNo()));
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 수정(커머스 API)")
    @PutMapping("")
    public ResponseEntity<ResOutput> updateProduct(@RequestBody ReqGoodsParam param) {
        log.debug("==================================================");
        log.debug("스마트스토어 상품 수정");
        log.debug("Param : {}", JsonUtil.objectToStr(param));
        log.debug("//================================================");

        String goodsImageUrl = productService.uploadProductImage(param.getGoodsImage());
        param.setGoodsImage(goodsImageUrl);
        log.debug("goodsImageUrl = {}", param.getGoodsImage());

        RegProductsRes regProductsRes = productService.updateProduct(param);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(regProductsRes);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 삭제 (커머스 API)")
    @DeleteMapping("/{channelId}")
    public ResponseEntity<ResOutput> deleteProduct(@PathVariable("channelId") String channelId) {
        log.debug("==================================================");
        log.debug("스마트스토어 상품 삭제");
        log.debug("Param : {}", channelId);
        log.debug("//================================================");
        CommerceCommonRes commerceCommonRes = productService.deleteProduct(channelId);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(commerceCommonRes);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 이미지 등록(커머스 API)")
    @PostMapping("/image")
    public ResponseEntity<ResOutput> uploadProductImage(@RequestParam("image") MultipartFile image) {
        log.debug("==================================================");
        log.debug("스마트스토어 상품 이미지 등록");
        log.debug("OriginalFilename : {}", image.getOriginalFilename());
        log.debug("//================================================");
        String url = productService.uploadProductImage(image);

        ResOutput resOutput = new ResOutput();
        resOutput.setData(url);
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 판매 상태 변경(커머스 API)")
    @PostMapping("/exhibitSt")
    public ResponseEntity<ResOutput> updateSaleStatus(@RequestBody ReqGoodsParam param) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 상품 판매 상태 변경");
        log.debug("param : {}", JsonUtil.objectToStr(param));
        log.debug("//================================================");

        ResOutput resOutput = new ResOutput();
        ArrayList<Good> goods = new ArrayList<>();

        if(param.getExhibitSt().equals("04")){
            resOutput = new ResOutput();
        }else{
            ProductInfoRes regProductsRes = productService.getProductInfo(param.getChannelId());
            RegProductsRes changeStatusRes = productService.updateSaleStatus(param, CommerceDataUtils.updateRegProductRequest(regProductsRes, param));
        }

        resOutput.setPage(new Page());
        resOutput.setData(goods);

        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}
