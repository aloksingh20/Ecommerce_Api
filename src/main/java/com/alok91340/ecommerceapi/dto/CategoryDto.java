package com.alok91340.ecommerceapi.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CategoryDto {
    private String categTitle;
    private String description;
    private Set<String> cateKeyword;
    private Set<CategoryDto> children;
}
