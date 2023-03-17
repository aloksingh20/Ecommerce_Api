package com.alok91340.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.dto.ProductDto;
import com.alok91340.ecommerceapi.repository.ProductRepository;
import com.alok91340.ecommerceapi.service.ProductService;
import com.alok91340.ecommerceapi.utils.Constant;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    private ProductRepository productRepository;

    // create product
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/createProduct", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductDto> createProduct(@RequestPart("productDto") ProductDto productDto,
            @RequestPart("file") MultipartFile file) {

        ProductDto responseProductDto = this.productService.createProduct(productDto, file);
        return new ResponseEntity<>(responseProductDto, HttpStatus.OK);
    }

    // create product with category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{categoryId}/saveProductByCategoryId")
    public ResponseEntity<ProductDto> saveProductByCategoryId(@PathVariable Long categoryId,
            @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = this.productService.saveProductByCategoryId(categoryId, productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.OK);
    }

    // get all product
    @GetMapping("/getAllProduct")
    private ResponseEntity<List<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION) String sortDir) {
        List<ProductDto> productDtos = productService.getAllProduct(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long categoryId,
            @RequestBody ProductDto productDto,
            @PathVariable Long productId) {
        // ProductDto productDtos = this.productService.getProductId(productId);
        ProductDto responseProduct = productService.updateProduct(categoryId,
                productDto, productId);
        return ResponseEntity.ok(responseProduct);
    }

    // delete product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product with id: " + productId + " is deleted successfully:)", HttpStatus.OK);
    }

    // search product
    @GetMapping("/search")
    public List<ProductDto> searchProduct(@RequestParam(value = "query") String query) {
        return productService.searchProduct(query);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable Long productId) {
        // ProductDto productDtos = this.productService.getProductId(productId);
        ProductDto responseProduct = productService.getProductId(productId);
        return ResponseEntity.ok(responseProduct);
    }
}
