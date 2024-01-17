package com.pongift.naver.controller;

import com.pongift.common.data.model.domain.dto.Category;
import com.pongift.common.data.model.domain.res.ResOutput;
import com.pongift.naver.data.model.res.CategoryListRes;
import com.pongift.naver.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    /*==============================================커머스 API==============================================*/
    @ApiOperation(value = "상품 카테고리 리스트 조회(커머스 API)")
    @GetMapping("/categories")
    public ResponseEntity<ResOutput> getCategoryList() {
        log.debug("==================================================");
        log.debug("네이버 커머스: 카테고리 조회");
        log.debug("//================================================");
        List<Category> categories = categoryService.getSubCategoryList("50007287");
        ResOutput out = new ResOutput();
        out.setList(categories);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @ApiOperation(value = "상품 카테고리 상세 조회(커머스 API)")
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResOutput> getCategoryInfo(@PathVariable("categoryId") String categoryId) {
        log.debug("==================================================");
        log.debug("네이버 커머스: 카테고리 조회");
        log.debug("//================================================");
        CategoryListRes categoryListRes = categoryService.getCategoryInfo(categoryId);
        ResOutput out = new ResOutput();
        out.setData(categoryListRes);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
