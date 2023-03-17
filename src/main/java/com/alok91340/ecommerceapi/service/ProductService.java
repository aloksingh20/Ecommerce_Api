package com.alok91340.ecommerceapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, MultipartFile file);

    List<ProductDto> getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductDto getProductId(Long productId);

    ProductDto updateProduct(Long categoryId, ProductDto productDto, Long productId);

    void deleteProduct(Long productId);

    ProductDto saveProductByCategoryId(Long categoryId, ProductDto productDto);

    List<ProductDto> searchProduct(String query);
}
