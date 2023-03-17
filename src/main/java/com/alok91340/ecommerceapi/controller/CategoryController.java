package com.alok91340.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.CategoryDto;
import com.alok91340.ecommerceapi.service.CategoryService;
import com.alok91340.ecommerceapi.utils.Constant;

@RestController
@RequestMapping(value = "api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create category api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto responseCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(responseCategory, HttpStatus.CREATED);
    }

    // get all categories api
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        List<CategoryDto> categoryResponse = categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    // get category by id api
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryDto responseCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(responseCategory);
    }

    // update category api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
            @PathVariable Long categoryId) {
        CategoryDto responseCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(responseCategory, HttpStatus.OK);
    }

    // delete category api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category with id: " + categoryId + " is deleted successfully:)");
    }
}