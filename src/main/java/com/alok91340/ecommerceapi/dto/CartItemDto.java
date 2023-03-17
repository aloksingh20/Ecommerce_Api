package com.alok91340.ecommerceapi.dto;

import com.alok91340.ecommerceapi.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CartItemDto {
    private long id;
    private Integer quantity;
    private String status;

    @JsonIgnore
    private Product product;
}
