package com.alok91340.ecommerceapi.service.Impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.dto.ProductDto;
import com.alok91340.ecommerceapi.entities.Category;
import com.alok91340.ecommerceapi.entities.Product;
import com.alok91340.ecommerceapi.repository.CategoryRepository;
import com.alok91340.ecommerceapi.repository.ProductRepository;
import com.alok91340.ecommerceapi.service.CommonService;
import com.alok91340.ecommerceapi.service.ProductService;

import javax.annotation.Resource;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile file) {

        productDto.setImage(uploadProductImage(file));
        Product product = mapToEntity(productDto);
        Product savedProduct = this.productRepository.save(product);
        ProductDto createProductDto = mapToDto(savedProduct);
        return createProductDto;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not available", productId));
        this.productRepository.delete(product);

    }

    @Override
    public List<ProductDto> getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);

        List<Product> productList = products.getContent();

        // map to dtos
        // System.out.println(productList.size());
        List<ProductDto> productDtoList = productList.stream()
                .map(product -> mapToDto(product))
                .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto getProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not available", productId));
        ProductDto productDto = mapToDto(product);
        return productDto;
    }

    @Override
    public ProductDto saveProductByCategoryId(Long categoryId, ProductDto productDto) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));

        Product product = mapToEntity(productDto);
        product.setCategory(category);

        Product savedProduct = this.productRepository.save(product);

        return mapToDto(savedProduct);
    }

    @Override
    public List<ProductDto> searchProduct(String query) {

        List<Product> products = this.productRepository.searchProduct(query);

        if (products.size() == 0) {
            throw new EcommerceApiException("No product is found", HttpStatus.BAD_REQUEST);
        }
        List<ProductDto> productDtoList = products.stream()
                .map(product -> mapToDto(product))
                .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto updateProduct(Long categoryId, ProductDto productDto, Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Not available ", productId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category ", productId));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setKeywords(productDto.getKeywords());
        product.setPrice(productDto.getPrice());
        product.setDetails(productDto.getDetails());
        product.setQuantity(productDto.getQuantity());
        product.setStatus(productDto.getStatus());
        product.setCategory(category);

        Product updatedProduct = this.productRepository.save(product);

        ProductDto updatedProductDto = mapToDto(updatedProduct);

        return updatedProductDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        return this.modelMapper.map(productDto, Product.class);
    }

    private String uploadProductImage(MultipartFile file) {
        ProductDto productDto = new ProductDto();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            logger.error("It is a valid file");
        }
        try {
            productDto.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productDto.getImage();
    }

    private ProductDto mapToDto(Product product) {
        return this.modelMapper.map(product, ProductDto.class);
    }
}
