package com.alok91340.ecommerceapi.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class ProductDto {
    private String title;
    private String description;
    private String details;
    private double price;
    private long quantity;
    private String status;
    private List<String> keywords;

    private String image;

    private Set<ImageDto> images;

    private Set<CommentDto> comments;
    private CategoryDto category;
}
