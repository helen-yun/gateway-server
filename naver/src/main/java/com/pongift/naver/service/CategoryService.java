package com.pongift.naver.service;

import com.pongift.common.data.model.domain.dto.Category;
import com.pongift.common.data.model.domain.res.ResOutput;

import java.util.ArrayList;
import java.util.List;

import com.pongift.naver.commerce.TokenService;
import com.pongift.naver.commerce.api.CommerceWebClient;
import com.pongift.naver.data.model.res.CategoryListRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final TokenService tokenService;
    private final CommerceWebClient commerceWebClient;


    /**
     * 전체 카테고리 조회
     *
     * @return
     */
    public ResOutput getAllCategoryList() {
        ResOutput out = new ResOutput();
        List<CategoryListRes> list = commerceWebClient.getCategoryList(tokenService.getAccessToken());
        List<Category> categories = new ArrayList<>();
        for (CategoryListRes categoryType : list) {
            if (categoryType.getWholeCategoryName().contains("e쿠폰")) {
                categories.add(new Category(categoryType.getId(), categoryType.getWholeCategoryName()));
            }
        }
        out.setList(categories);
        return out;
    }

    /**
     * 카테고리 상세 조회
     *
     * @return
     */
    public CategoryListRes getCategoryInfo(String categoryId) {
        CategoryListRes categoryListRes = commerceWebClient.getCategoryInfo(tokenService.getAccessToken(), categoryId);
        return categoryListRes;
    }

    /**
     * 하위 카테고리 조회
     *
     * @return
     */
    public List<Category> getSubCategoryList(String categoryId) {
        List<CategoryListRes> categoryListResList = commerceWebClient.getSubCategoryList(tokenService.getAccessToken(), categoryId);
        List<Category> categories = new ArrayList<>();
        for (CategoryListRes categoryType : categoryListResList) {
            categories.add(new Category(categoryType.getId(), categoryType.getWholeCategoryName()));
        }
        return categories;
    }
}
