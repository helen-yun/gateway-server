package com.pongift.naver.data.model.commerce;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * MultipartFile JSON 직렬화 매핑 클래스
 */
public class MultipartResource implements Resource {
    private final MultipartFile multipartFile;

    public MultipartResource(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String getFilename() {
        return multipartFile.getOriginalFilename();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public URL getURL() throws IOException {
        return multipartFile.getResource().getURL();
    }

    @Override
    public URI getURI() throws IOException {
        return multipartFile.getResource().getURI();
    }

    @Override
    public File getFile() throws IOException {
        return multipartFile.getResource().getFile();
    }

    @Override
    public long contentLength() {
        return multipartFile.getSize();
    }

    @Override
    public long lastModified() {
        return 0;
    }

    @Override
    public Resource createRelative(String relativePath) {
        return null;
    }
}