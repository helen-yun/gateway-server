package com.pongift.naver.data.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 카테고리 조회
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryListRes {
    /**
     * 전체 카테고리명
     */
    private String wholeCategoryName;
    /**
     * 카테고리 ID
     */
    private String id;
    /**
     * 카테고리명
     */
    private String name;
    /**
     * 리프 카테고리 여부
     */
    private boolean last;
    /**
     * 예외 카테고리 목록
     */
    private List<String> exceptionalCategories;

    /**
     * error
     */
    private String timestamp;
    private String code;
    private String message;
}
