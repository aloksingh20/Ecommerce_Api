package com.alok91340.ecommerceapi.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private double productPrice;
    private Integer productQuantity;
    private double totalPrice;
    private String note;
    private String status;
}
