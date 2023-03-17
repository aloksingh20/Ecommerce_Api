package com.alok91340.ecommerceapi.service.Impl;

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
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.dto.CategoryDto;
import com.alok91340.ecommerceapi.entities.Category;
import com.alok91340.ecommerceapi.repository.CategoryRepository;
import com.alok91340.ecommerceapi.service.CategoryService;
import com.alok91340.ecommerceapi.service.CommonService;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = mapToEntity(categoryDto);
        Category savedCategory = this.categoryRepository.save(category);

        return mapToDto(savedCategory);

    }

    @Override
    public List<CategoryDto> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> category = this.categoryRepository.findAll(pageable);

        List<Category> categoryList = category.getContent();

        List<CategoryDto> categoryDtoList = categoryList.stream()
                .map(categor -> mapToDto(categor))
                .collect(Collectors.toList());

        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", categoryId));
        CategoryDto categoryDto = mapToDto(category);
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found", categoryId));

        category.setCategTitle(categoryDto.getCategTitle());
        category.setCateKeyword(categoryDto.getCateKeyword());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory = this.categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", categoryId));
        this.categoryRepository.delete(category);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategTitle(categoryDto.getCategTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCateKeyword(categoryDto.getCateKeyword());
        return category;
    }

    private CategoryDto mapToDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }

}
