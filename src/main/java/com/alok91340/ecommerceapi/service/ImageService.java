package com.alok91340.ecommerceapi.service;

import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.entities.Images;

public interface ImageService {
    Images addImageToProduct(Long productId, MultipartFile[] file);
}
