package com.pongift.naver.data.model.res;

import lombok.Data;

import java.util.List;

@Data
public class ImageUploadRes {
    private List<Image> images;

    @Data
    public static class Image {
        private String url;
    }
}
