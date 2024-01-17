package com.pongift.marketplace.controller;

import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.common.utils.JsonUtil;
import com.pongift.marketplace.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    /**
     * 제휴몰 유통 카테고리 조회
     * @return
     */
    @ApiOperation(value = "제휴몰 카테고리 조회")
    @GetMapping("")
    public ResponseEntity<ResOutput> selectCategory() {
        log.info("==================================================");
        log.info("제휴몰 카테고리 조회");
        log.info("//================================================");

        ResOutput resOutput = categoryService.selectCategory();
        return new ResponseEntity<>(resOutput, HttpStatus.OK);
    }
}

