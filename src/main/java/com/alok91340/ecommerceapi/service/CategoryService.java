package com.alok91340.ecommerceapi.service;

import java.util.List;

import com.alok91340.ecommerceapi.dto.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir);

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);
}
