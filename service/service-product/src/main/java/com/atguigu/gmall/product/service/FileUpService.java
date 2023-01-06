package com.atguigu.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpService {
    String upFile(MultipartFile file) throws Exception;
}
