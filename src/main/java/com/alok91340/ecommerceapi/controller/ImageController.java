package com.alok91340.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.entities.Images;
import com.alok91340.ecommerceapi.service.ImageService;

@RestController
@RequestMapping("api/v1/products")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/{productId}/addImage")
    public ResponseEntity<String> addImageToProduct(@PathVariable Long productId,
            @RequestParam("file") MultipartFile[] file) {
        Images images = imageService.addImageToProduct(productId, file);
        return new ResponseEntity<>("Image  successfully added to product with id : " + productId, HttpStatus.CREATED);

    }
}
