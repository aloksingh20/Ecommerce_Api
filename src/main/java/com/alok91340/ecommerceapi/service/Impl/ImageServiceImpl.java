package com.alok91340.ecommerceapi.service.Impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.entities.Images;
import com.alok91340.ecommerceapi.entities.Product;
import com.alok91340.ecommerceapi.repository.ImageRepository;
import com.alok91340.ecommerceapi.repository.ProductRepository;
import com.alok91340.ecommerceapi.service.ImageService;
import com.alok91340.ecommerceapi.utils.ImageUtils;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Images addImageToProduct(Long productId, MultipartFile[] file) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        Images addImage = null;
        imageRepository.saveAll(uploadImage(file, product));
        return addImage;
    }

    private Set<Images> uploadImage(MultipartFile[] files, Product product) {
        Set<Images> image = product.getImages();
        Set<Images> images = new HashSet<>();
        Images imageData = new Images();
        for (MultipartFile file : files) {
            imageData = new Images();
            imageData.setImgName(file.getOriginalFilename());
            imageData.setImgType(file.getContentType());
            try {
                imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
            } catch (IOException e) {

                e.printStackTrace();
            }
            image.add(imageData);
            imageData.setProduct(product);
            images.add(imageData);
        }
        return images;

    }

}
